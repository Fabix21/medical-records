package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.entities.Tag;
import com.medicalrecords.medicalrecords.repositories.DocumentationRepository;
import com.medicalrecords.medicalrecords.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Service
public class DocumentationService {

    private final AmazonClientService amazonClientService;
    private final DocumentationRepository documentationRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final TagRepository tagRepository;

    @Autowired
    public DocumentationService( final AmazonClientService amazonClientService,
                                 final DocumentationRepository documentationRepository,
                                 final DoctorService doctorService,final PatientService patientService,TagRepository tagRepository ) {
        this.amazonClientService = amazonClientService;
        this.documentationRepository = documentationRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.tagRepository = tagRepository;
    }

    @CacheEvict(value = "getAllDocuments", allEntries = true)
    public void saveDocument( MultipartFile file,
                              final String login,
                              final String issuedBy,
                              final Set<Tag> tags ) throws Exception {
        final Patient patient = patientService.getPatient(login);
        final String fileName = amazonClientService.uploadFile(file);
        final Doctor doctor = doctorService.getDoctor(issuedBy);


        final Documentation documentation = Documentation.builder()
                                                         .patient(patient)
                                                         .timestamp(new Timestamp(System.currentTimeMillis()))
                                                         .doctor(doctor)
                                                         .tags(tags)
                                                         .documentName(file.getOriginalFilename())
                                                         .s3path(fileName).build();

        tags.forEach(tag -> tag.getDocumentations().add(documentation));

        documentationRepository.save(documentation);
    }

    @Cacheable(value = "getAllDocuments")
    public List<Documentation> getAllDocuments( String login ) {
        final Patient patient = patientService.getPatient(login);
        return patient.getMedicalDocumentations();
    }
}

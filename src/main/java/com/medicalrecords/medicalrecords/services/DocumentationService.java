package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.repositories.DocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DocumentationService {

    private final AmazonClientService amazonClientService;
    private final DocumentationRepository documentationRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public DocumentationService( final AmazonClientService amazonClientService,
                                 final DocumentationRepository documentationRepository,
                                 final DoctorService doctorService,final PatientService patientService ) {
        this.amazonClientService = amazonClientService;
        this.documentationRepository = documentationRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @CacheEvict(value = "getAllDocuments", allEntries = true)
    public void saveDocument( MultipartFile file,String login,String issuedBy ) throws Exception {
        final Patient patient = patientService.getPatient(login);
        final String fileName = amazonClientService.uploadFile(file);
        final Documentation documentation = Documentation.builder()
                                                         .patient(patient)
                                                         .timestamp(new Timestamp(System.currentTimeMillis()))
                                                         .issuedBy(doctorService.getDoctorTitle(issuedBy))
                                                         .documentName(file.getOriginalFilename())
                                                         .s3path(fileName).build();
        documentationRepository.save(documentation);
    }

    @Cacheable(value = "getAllDocuments")
    public List<Documentation> getAllDocuments( String login ) {
        final Patient patient = patientService.getPatient(login);
        return patient.getMedicalDocumentations();
    }
}

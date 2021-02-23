package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.entities.Tag;
import com.medicalrecords.medicalrecords.repositories.DocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @CacheEvict(cacheNames = {"getAllDocuments","getAllDocuments","getDocumentsByDate"}, allEntries = true)
    public void saveDocument( MultipartFile file,
                              final String login,
                              final String issuedBy,
                              final Set<Tag> tags ) throws Exception {
        final Patient patient = patientService.getPatient(login);
        final String fileName = amazonClientService.uploadFile(file);
        final Doctor doctor = doctorService.getDoctor(issuedBy);
        final Documentation documentation = Documentation.builder()
                                                         .patient(patient)
                                                         .date(LocalDate.now())
                                                         .doctor(doctor)
                                                         .tags(tags)
                                                         .documentName(file.getOriginalFilename())
                                                         .s3path(fileName).build();
        tags.forEach(tag -> {
            tag.setDocumentations(new HashSet<>());
            tag.getDocumentations().add(documentation);
        });

        documentationRepository.save(documentation);
    }
    @CacheEvict(cacheNames = {"getAllDocuments","getAllDocuments","getDocumentsByDate"}, allEntries = true)
    public void saveDocumentByIds( MultipartFile file,
                              final long patID,
                              final long docID,
                              final Set<Tag> tags ) throws Exception {
        final Patient patient = patientService.getPatientById(patID);
        final Doctor doctor = doctorService.getDoctorById(docID);
        final String fileName = amazonClientService.uploadFile(file);
        final Documentation documentation = Documentation.builder()
                                                         .patient(patient)
                                                         .date(LocalDate.now())
                                                         .doctor(doctor)
                                                         .tags(tags)
                                                         .documentName(file.getOriginalFilename())
                                                         .s3path(fileName).build();
        tags.forEach(tag -> {
            tag.setDocumentations(new HashSet<>());
            tag.getDocumentations().add(documentation);
        });

        documentationRepository.save(documentation);
    }

    @Cacheable(value = "getAllDocuments")
    public List<Documentation> getAllDocuments( String login ) {
        final Patient patient = patientService.getPatient(login);
        return patient.getMedicalDocumentations();
    }

    @Cacheable(value = "getDocumentsIssuedByDoctor")
    public List<Documentation> getDocumentsIssuedByDoctor( String login ) {
        final Doctor doctor = doctorService.getDoctor(login);
        return doctor.getDocumentations();
    }

    @Cacheable(value = "getDocumentsByDate")
    public List<Documentation> getDocumentsByDate( LocalDate from,LocalDate to ) {
        return documentationRepository.findByDateBetween(from,to);
    }

    public List<Documentation> getDocumentsByPatientId( long id ) {
        final Patient patient = patientService.getPatientById(id);
        return patient.getMedicalDocumentations();
    }

    public List<Documentation> getDocumentsByDoctorId( long id ) {
        final Doctor doctor = doctorService.getDoctorById(id);
        return doctor.getDocumentations();
    }
}

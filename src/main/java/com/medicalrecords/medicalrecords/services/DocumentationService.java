package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.entities.User;
import com.medicalrecords.medicalrecords.repositories.DocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentationService {

    private final AmazonClientService amazonClientService;
    private final UserService userService;
    private final DocumentationRepository documentationRepository;

    @Autowired
    public DocumentationService( final AmazonClientService amazonClientService,final UserService userService,
                                 final DocumentationRepository documentationRepository ) {
        this.amazonClientService = amazonClientService;
        this.userService = userService;
        this.documentationRepository = documentationRepository;
    }

    @Transactional
    public String saveDocument( MultipartFile file,String login ) throws Exception {
        final User user = userService.getUser(login);
        final String fileName = amazonClientService.uploadFile(file);
        final Documentation documentation = Documentation.builder()
                                                         .user(user)
                                                         .timestamp(new Timestamp(System.currentTimeMillis()))
                                                         .s3path(fileName).build();
        documentationRepository.save(documentation);
        return "Document: " + fileName + " for user " + login + " has been saved!";
    }

    public List<String> getAllDocuments( String login ) {
        final User user = userService.getUser(login);
        final List<Documentation> medicalDocumentations = user.getMedicalDocumentations();
        return medicalDocumentations.stream()
                                    .map(Documentation::getS3path)
                                    .collect(Collectors.toList());
    }
}

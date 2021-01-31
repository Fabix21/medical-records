package com.medicalrecords.medicalrecords.controllers;

import com.medicalrecords.medicalrecords.services.DocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentationController {
    private final DocumentationService documentationService;

    @Autowired
    public DocumentationController( final DocumentationService documentationService ) {
        this.documentationService = documentationService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile( @RequestPart(value = "file") MultipartFile file,
                              @RequestPart(value = "login") String login ) throws Exception {
        return documentationService.saveDocument(file,login);
    }
}

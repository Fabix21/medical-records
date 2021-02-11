package com.medicalrecords.medicalrecords.controllers.rest;

import com.medicalrecords.medicalrecords.dto.DocumentDTO;
import com.medicalrecords.medicalrecords.entities.Tag;
import com.medicalrecords.medicalrecords.services.AmazonClientService;
import com.medicalrecords.medicalrecords.services.DocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class DocumentationControllerREST {
    private final DocumentationService documentationService;
    private final AmazonClientService amazonClientService;

    @Autowired
    public DocumentationControllerREST( final DocumentationService documentationService,final AmazonClientService amazonClientService ) {
        this.documentationService = documentationService;
        this.amazonClientService = amazonClientService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile( @RequestParam("file") MultipartFile file,
                              @RequestParam("patient") String patientLogin,
                              @RequestParam("doctor") String issuedBy,
                              @RequestParam("tags") Set<String> tagNames ) throws Exception {

        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            tags.add(Tag.builder()
                        .tagName(tagName)
                        .build());
        }
        documentationService.saveDocument(file,patientLogin,issuedBy,tags);
        return "Document has been added succesfully!";
    }

    @GetMapping("/getDocuments/patients")
    public List<DocumentDTO> getDocumentationByPatient( @RequestParam("patient") String patient ) {

        return documentationService.getAllDocuments(patient).stream()
                                   .map(DocumentDTO::new)
                                   .collect(Collectors.toList());

    }

    @GetMapping("/getDocuments/doctors")
    public List<DocumentDTO> getDocumentationByDoctor( @RequestParam("doctor") String doctor ) {
        return documentationService.getDocumentsIssuedByDoctor(doctor).stream()
                                   .map(DocumentDTO::new)
                                   .collect(Collectors.toList());
    }

    @GetMapping("/getDocuments/date")
    public List<DocumentDTO> getDocumentationByDate( @RequestParam("from")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             LocalDate from,
                                                     @RequestParam("to")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             LocalDate to ) {
        return documentationService.getDocumentsByDate(from,to).stream()
                                   .map(DocumentDTO::new)
                                   .collect(Collectors.toList());
    }/*
    @GetMapping("/getDocuments/tag")
    public Set<DocumentDTO> getDocumentationByTag( @RequestParam("tag") String tag) {
        DocumentDTO documentDTO = new DocumentDTO();
        return documentationService.getDocumentsByTags(tag).stream()
                                                      .map(documentDTO::getDto)
                                                      .collect(Collectors.toSet());
    }*/

    @GetMapping("/getDocuments/{docName}")
    public ResponseEntity<byte[]> getFile( @PathVariable String docName ) {
        byte[] bytes = amazonClientService.downloadFile(docName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=" + docName);

        return ResponseEntity.ok()
                             .headers(headers)
                             .contentLength(bytes.length)
                             .contentType(MediaType.parseMediaType("application/pdf")).body(bytes);
    }
}

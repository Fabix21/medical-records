package com.medicalrecords.medicalrecords.controllers.rest;

import com.medicalrecords.medicalrecords.dto.DocumentDTO;
import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.entities.Tag;
import com.medicalrecords.medicalrecords.services.AmazonClientService;
import com.medicalrecords.medicalrecords.services.DocumentationService;
import com.medicalrecords.medicalrecords.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class DocumentationControllerREST {
    private final DocumentationService documentationService;
    private final TagService tagService;
    private final AmazonClientService amazonClientService;

    @Autowired
    public DocumentationControllerREST( final DocumentationService documentationService,
                                        final TagService tagService,AmazonClientService amazonClientService) {
        this.documentationService = documentationService;
        this.tagService = tagService;
        this.amazonClientService = amazonClientService;
    }

    @PostMapping("/documents")
    public String uploadFile( @RequestParam("file") MultipartFile file,
                              @RequestParam("patID") long patID,
                              @RequestParam("docID") long docID,
                              @RequestParam("tags") Set<String> tagNames ) throws Exception {

        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            tags.add(Tag.builder()
                        .tagName(tagName)
                        .build());
        }
        documentationService.saveDocumentByIds(file,patID,docID,tags);
        return "Document has been added succesfully!";
    }

    @GetMapping("/documents/patients")
    public List<DocumentDTO> getDocumentationByPatient( @RequestParam("patient") String patient ) {

        return documentationService.getAllDocuments(patient).stream()
                                   .map(DocumentDTO::new)
                                   .collect(Collectors.toList());

    }

    @GetMapping("/documents/doctors")
    public List<DocumentDTO> getDocumentationByDoctor( @RequestParam("doctor") String doctor ) {
        return documentationService.getDocumentsIssuedByDoctor(doctor).stream()
                                   .map(DocumentDTO::new)
                                   .collect(Collectors.toList());
    }

    @GetMapping("/documents/date")
    public List<DocumentDTO> getDocumentationByDate( @RequestParam("from")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             LocalDate from,
                                                     @RequestParam("to")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             LocalDate to ) {
        return documentationService.getDocumentsByDate(from, to).stream()
                                   .map(DocumentDTO::new)
                                   .collect(Collectors.toList());
    }

    @GetMapping("/documents/tag")
    public List<DocumentDTO> getDocumentationByTag( @RequestParam("tag") String tag) {

        return tagService.getDocumentsByTag(tag)
                         .stream()
                         .map(DocumentDTO::new)
                         .collect(Collectors.toList());
    }

    @GetMapping("/documents/{docName}")
    public ResponseEntity<byte[]> getFile( @PathVariable String docName ) {
        byte[] bytes = amazonClientService.downloadFile(docName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=" + docName);

        return ResponseEntity.ok()
                             .headers(headers)
                             .contentLength(bytes.length)
                             .contentType(MediaType.parseMediaType("application/pdf")).body(bytes);
    }

    @GetMapping("/documents/patients/{id}")
    public List<DocumentDTO> getDocumentationByPatient( @PathVariable long id ) {
        return documentationService.getDocumentsByPatientId(id)
                                   .stream()
                                   .map(DocumentDTO::new)
                                   .collect(Collectors.toList());
    }
    @GetMapping("/documents/doctors/{id}")
    public List<DocumentDTO> getDocumentationByDoctor(@PathVariable long id ) {
        return documentationService.getDocumentsByDoctorId(id)
                                   .stream()
                                   .map(DocumentDTO::new)
                                   .collect(Collectors.toList());
    }
}

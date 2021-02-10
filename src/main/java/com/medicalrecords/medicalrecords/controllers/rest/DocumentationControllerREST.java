package com.medicalrecords.medicalrecords.controllers.rest;

import com.medicalrecords.medicalrecords.services.AmazonClientService;
import com.medicalrecords.medicalrecords.services.DocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentationControllerREST {
    private final DocumentationService documentationService;
    private final AmazonClientService amazonClientService;

    @Autowired
    public DocumentationControllerREST( final DocumentationService documentationService,final AmazonClientService amazonClientService ) {
        this.documentationService = documentationService;
        this.amazonClientService = amazonClientService;
    }

    // @PostMapping("/uploadFile")
    // public String uploadFile( @RequestParam("file") MultipartFile file,
    //                          @RequestParam("selectedUser") String login) throws Exception {
    //     return documentationService.saveDocument (file, login);
    //  }


    //   @GetMapping("/getDocuments")
    // public List<String> getDocuments( @RequestPart(value = "login") String login ) {
    //     return documentationService.getAllDocuments(login);
    //  }

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

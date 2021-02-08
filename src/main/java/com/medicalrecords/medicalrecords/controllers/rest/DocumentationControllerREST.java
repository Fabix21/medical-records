package com.medicalrecords.medicalrecords.controllers.rest;

import com.medicalrecords.medicalrecords.services.AmazonClientService;
import com.medicalrecords.medicalrecords.services.DocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public DocumentationControllerREST( final DocumentationService documentationService,AmazonClientService amazonClientService ) {
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
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(bytes,headers,HttpStatus.OK);
    }
}

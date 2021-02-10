package com.medicalrecords.medicalrecords.controllers;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.security.UserPrincipal;
import com.medicalrecords.medicalrecords.services.DocumentationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class DocumentationController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationController.class);

    private final DocumentationService documentationService;

    @Autowired
    public DocumentationController( final DocumentationService documentationService ) {
        this.documentationService = documentationService;
    }

    @PostMapping("/upload")
    public String newDocument( @RequestParam("selectedUser") final String selectedUser,
                               @RequestParam("file") final MultipartFile docFile,
                               @AuthenticationPrincipal final UserPrincipal<Doctor> issuedBy,
                               final RedirectAttributes attributes ) throws Exception {
        String doctorUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        documentationService.saveDocument(docFile,selectedUser,issuedBy.getUsername());
        logger.info("Doctor in session: {}",doctorUsername);
        attributes.addFlashAttribute("message","Pomyślnie dodano dokumentacje");
        return "redirect:patients";
    }

    @GetMapping("/checkDoc")
    public String checkDocumentation( Model model ) {
        String patientUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String context = SecurityContextHolder.getContextHolderStrategy().toString();
        logger.info("Patient in session: {}",patientUsername);
        logger.info("Context: {}",context);
        List<Documentation> documentsNames = documentationService.getAllDocuments(patientUsername);
        model.addAttribute("documentations",documentsNames);
        return "checkDoc";
    }
}

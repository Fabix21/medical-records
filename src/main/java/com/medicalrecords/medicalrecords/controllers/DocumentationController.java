package com.medicalrecords.medicalrecords.controllers;

import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.security.DoctorPrincipal;
import com.medicalrecords.medicalrecords.security.PatientPrincipal;
import com.medicalrecords.medicalrecords.services.DocumentationService;
import com.medicalrecords.medicalrecords.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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


    @Autowired
    DocumentationService documentationService;
    @Autowired
    PatientService patientService;

    /*
    @GetMapping("/addDoc")
    public String addDoc (Model model) {
        List<String> users =  patientService.getAllPatients;
        model.addAttribute("usersLogin", users);
        return "addDoc";
    }*/
    @PostMapping("/upload")
    public String newDocument( @RequestParam("selectedUser") final String selectedUser,
                               @RequestParam("file") final MultipartFile docFile,
                               @AuthenticationPrincipal DoctorPrincipal issuedBy,
                               //@RequestParam("currentPage") final String currentPage,
                               RedirectAttributes attributes ) throws Exception {

        documentationService.saveDocument(docFile,selectedUser,issuedBy.getUsername());
        attributes.addFlashAttribute("message","Pomyślnie dodano dokumentacje");

        return "redirect:patients/1/5";
    }

    @GetMapping("/checkDoc")
    public String checkDocumentation( @AuthenticationPrincipal PatientPrincipal patient,Model model ) {
        List<Documentation> documentsNames = documentationService.getAllDocuments(patient.getUsername());
        model.addAttribute("documentations",documentsNames);
        return "checkDoc";
    }
}

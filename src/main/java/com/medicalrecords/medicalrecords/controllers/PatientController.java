package com.medicalrecords.medicalrecords.controllers;

import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    @Autowired
    public PatientController( final PatientService patientService ) {
        this.patientService = patientService;
    }


    @PostMapping("/newPatient")
    public String addUser( @ModelAttribute final Patient newPatient,
                           final RedirectAttributes attributes ) {
        patientService.save(newPatient);
        attributes.addFlashAttribute("message","Pomyślnie dodano Pacjenta");
        return "redirect:patients";
    }

    @GetMapping("/addPatient")
    public String addPatient( final Model model ) {
        Patient patient = new Patient();
        model.addAttribute("patient",patient);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Doctor in session : {}",currentUsername);
        return "addPatient";
    }

    @GetMapping("/patients")
    public String patientsPaginatedList( final Model model ) {
        List<Patient> patients = patientService.getAll();
        model.addAttribute("listOfUsers",patients);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Doctor in session : {}",currentUsername);
        return "patients";
    }
}

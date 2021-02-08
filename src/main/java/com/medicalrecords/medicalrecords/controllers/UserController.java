package com.medicalrecords.medicalrecords.controllers;

import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.security.DoctorPrincipal;
import com.medicalrecords.medicalrecords.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final PatientService patientService;

    @Autowired
    public UserController( final PatientService patientService ) {
        this.patientService = patientService;
    }

    @GetMapping("/patients/{pageNum}/{pageSize}")
    public String findPaginated( @PathVariable int pageNum,Model model ) {
        int pageSize = 5;

        Page<Patient> page = patientService.findPaginated(pageNum,pageSize);
        List<Patient> listOfUsers = page.getContent();

        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalElements",page.getTotalElements());
        model.addAttribute("listOfUsers",listOfUsers);
        return "patients";
    }

    @PostMapping("/newPatient")
    public String addUser( @ModelAttribute Patient newPatient,@AuthenticationPrincipal DoctorPrincipal doctor ) {
        Patient patient = newPatient;
        patientService.save(patient,doctor.getUsername());
        return "Patient has been successfully added!";
    }

    @GetMapping("/addPatient")
    public String addPatient( Model model ) {
        Patient patient = new Patient();
        model.addAttribute("patient",patient);
        return "addPatient";
    }
}

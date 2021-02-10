package com.medicalrecords.medicalrecords.controllers;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.services.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController( final DoctorService doctorService ) {
        this.doctorService = doctorService;
    }

    @PostMapping("/newDoctor")
    public String newDoctor( @ModelAttribute final Doctor doctor,
                             final RedirectAttributes attributes ) {
        doctorService.save(doctor);
        attributes.addFlashAttribute("message","Pomyślnie dodano Lekarza");
        return "redirect:addDoctor";
    }

    @GetMapping("/addDoctor")
    public String addDoctor( final Model model ) {
        Doctor doctor = new Doctor();
        model.addAttribute("doctor",doctor);
        return "addDoctor";
    }
}

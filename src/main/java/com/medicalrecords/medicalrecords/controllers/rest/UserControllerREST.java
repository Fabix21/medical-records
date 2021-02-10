package com.medicalrecords.medicalrecords.controllers.rest;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerREST {

    private final DoctorService doctorService;

    @Autowired
    public UserControllerREST( final DoctorService doctorService ) {
        this.doctorService = doctorService;
    }

    @PostMapping("/addDoctor")
    public String addUser( @RequestBody final Doctor newDoctor ) {
        doctorService.save(newDoctor);
        return "Doctor has been successfully added!";
    }

}

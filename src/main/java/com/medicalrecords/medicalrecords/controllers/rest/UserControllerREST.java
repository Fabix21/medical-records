package com.medicalrecords.medicalrecords.controllers.rest;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.services.DoctorService;
import com.medicalrecords.medicalrecords.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerREST {
    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;


    @PostMapping("/addDoctor")
    public String addUser( @RequestBody Doctor newDoctor ) {
        doctorService.save(newDoctor);
        return "Doctor has been successfully added!";
    }

}

package com.medicalrecords.medicalrecords.controllers.rest;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.services.DoctorService;
import com.medicalrecords.medicalrecords.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerREST {

    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public UserControllerREST( final DoctorService doctorService,PatientService patientService ) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping("/addDoctor")
    public String addDoctor( @RequestBody final Doctor newDoctor ) {
        doctorService.save(newDoctor);
        return "Doctor has been successfully added!";
    }

    @PostMapping("/addPatient")
    public String addPatient( @RequestBody final Patient newPatient ) {
        patientService.save(newPatient);
        return "Patient has been successfully added!";
    }
}

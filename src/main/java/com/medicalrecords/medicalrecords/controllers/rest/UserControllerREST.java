package com.medicalrecords.medicalrecords.controllers.rest;

import com.medicalrecords.medicalrecords.dto.DoctorDTO;
import com.medicalrecords.medicalrecords.dto.PatientDTO;
import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.services.DoctorService;
import com.medicalrecords.medicalrecords.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserControllerREST {

    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public UserControllerREST( final DoctorService doctorService,PatientService patientService ) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping("/user/doctors")
    public String addDoctor( @RequestBody final Doctor newDoctor ) {
        doctorService.save(newDoctor);
        return "Doctor has been successfully added!";
    }

    @GetMapping("/user/doctors/{id}")
    public DoctorDTO getDoctorById( @PathVariable long id ) {
        return doctorService.getDoctorDTOById(id);
    }

    @GetMapping("/user/doctors")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping("/user/patients")
    public String addPatient( @RequestBody final Patient newPatient ) {
        patientService.save(newPatient);
        return "Patient has been successfully added!";
    }

    @GetMapping("/user/patients/{id}")
    public PatientDTO getPatientById( @PathVariable long id ) {
        return patientService.getPatientDTOById(id);
    }

    @GetMapping("/user/patients")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }
}

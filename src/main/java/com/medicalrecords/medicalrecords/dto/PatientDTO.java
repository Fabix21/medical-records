package com.medicalrecords.medicalrecords.dto;

import com.medicalrecords.medicalrecords.entities.Patient;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {

    private final String name;
    private final String email;
    private final String pesel;
    private final LocalDate dateOfBirth;
    private final String nfzID;

}

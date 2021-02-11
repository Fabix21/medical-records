package com.medicalrecords.medicalrecords.dto;

import com.medicalrecords.medicalrecords.entities.Doctor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DoctorDTO {

    private final String name;
    private final String email;
    private final String surname;
    private final String pesel;
    private final LocalDate dateOfBirth;
    private final String licenceNumber;
    private final List<String> medSpecialization;

    public DoctorDTO( final Doctor doctor ) {
        this.name = doctor.getName().concat(" ").concat(doctor.getSurname());
        this.email = doctor.getEmail();
        this.surname = doctor.getSurname();
        this.pesel = doctor.getPesel();
        this.dateOfBirth = doctor.getDateOfBirth();
        this.licenceNumber = doctor.getLicenceNumber();
        this.medSpecialization = doctor.getMedSpecialization();
    }
}

package com.medicalrecords.medicalrecords.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Doctor extends User {

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<Documentation> documentations;

    @NotBlank
    @Size(max = 20)
    private String name;

    @Email
    @Size(max = 30)
    private String email;

    @NotBlank
    @Size(max = 20)
    private String surname;

    @Size(max = 11)
    private String pesel;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    private String licenceNumber;

    @ElementCollection
    private List<String> medSpecialization;
}

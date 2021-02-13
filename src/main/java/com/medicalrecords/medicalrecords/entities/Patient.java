package com.medicalrecords.medicalrecords.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Patient extends User {

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

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Documentation> medicalDocumentations;

    @NotBlank
    private String nfzID;


}

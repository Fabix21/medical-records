package com.medicalrecords.medicalrecords.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {

    private static final String ROLE = "DOC";

    //@ManyToMany
    // private Set<Patient> patients = new HashSet<>();

    @NotBlank
    private String licenceNumber;

    // @ElementCollection
    //private List<String> medSpecialization ;
}

package com.medicalrecords.medicalrecords.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {

   private static final String ROLE = "PAT";

   // @ManyToMany
   //private Set<Doctor> doctors;

   @OneToMany(mappedBy = "patient")
   private List<Documentation> medicalDocumentations;

   @NotBlank
   private String nfzID;


}

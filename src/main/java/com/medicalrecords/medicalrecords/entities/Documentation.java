package com.medicalrecords.medicalrecords.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Documentation {

    @ManyToOne
    private Patient patient;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String s3path;

    @NotNull
    private Timestamp timestamp;

    @NotBlank
    private String documentName;

    @NotBlank
    private String issuedBy;
}

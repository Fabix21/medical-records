package com.medicalrecords.medicalrecords.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;


@Data
@Entity
public class User {
    @OneToMany(mappedBy = "user")
    List<Documentation> medicalDocumentations;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String login;

    @Email
    @Size(max = 20)
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String surname;

    @NotBlank
    private String pesel;

    @NotBlank
    private String nfzID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotBlank
    private String role;

    public String setPassword( String password ) {
        this.password = password;
        return password;
    }
}

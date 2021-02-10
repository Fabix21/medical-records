package com.medicalrecords.medicalrecords.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String login;

    @NotBlank
    @Size(min = 5)
    private String password;

    @Length(max = 3)
    private String role;

    public String setPassword( String password ) {
        this.password = password;
        return password;
    }
}

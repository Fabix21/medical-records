package com.medicalrecords.medicalrecords.entities;

import lombok.Data;
import javax.persistence.*;
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
    private String login;
    private String email;
    private String name;
    private String password;
    private String surname;
    private String pesel;
    private String nfzID;
    private Date dateOfBirth;
    private String role;

    public String setPassword( String password ) {
        this.password = password;
        return password;
    }
}

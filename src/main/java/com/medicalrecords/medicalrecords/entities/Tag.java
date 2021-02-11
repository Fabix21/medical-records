package com.medicalrecords.medicalrecords.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Documentation> documentations = new HashSet<>();

    private String tagName;

}

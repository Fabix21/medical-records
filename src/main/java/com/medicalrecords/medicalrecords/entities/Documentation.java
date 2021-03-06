package com.medicalrecords.medicalrecords.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Documentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "documentation_tag",
            joinColumns = @JoinColumn(name = "documentation_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @NotBlank
    private String s3path;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String documentName;

}

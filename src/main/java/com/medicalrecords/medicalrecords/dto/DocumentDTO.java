package com.medicalrecords.medicalrecords.dto;

import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.entities.Tag;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class DocumentDTO {

    private final String documentName;
    private final String doctorName;
    private final String s3Path;
    private final LocalDate timestamp;
    private final Set<String> tags;
    private final PatientDTO patient;
    private final DoctorDTO doctor;

    public DocumentDTO( final Documentation doc ) {
        this.documentName = doc.getDocumentName();
        this.doctorName = doc.getDoctor().getName().concat(" ").concat(doc.getDoctor().getSurname());
        this.s3Path = doc.getS3path();
        this.timestamp = doc.getDate();
        this.tags = doc.getTags()
                       .stream()
                       .map(Tag::getTagName)
                       .collect(Collectors.toSet());
        this.patient = new PatientDTO(doc.getPatient());
        this.doctor = new DoctorDTO(doc.getDoctor());
    }


}

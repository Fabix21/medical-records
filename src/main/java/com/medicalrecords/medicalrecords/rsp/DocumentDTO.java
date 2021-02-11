package com.medicalrecords.medicalrecords.rsp;

import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.entities.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class DocumentDTO {

    private String documentName;
    private String doctorName;
    private String s3Path;
    private LocalDate timestamp;
    private Set<String> tags;

    public DocumentDTO getDto( Documentation doc ) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDocumentName(doc.getDocumentName());
        documentDTO.setDoctorName(doc.getDoctor().getName() + " " + doc.getDoctor().getSurname());
        documentDTO.setS3Path(doc.getS3path());
        documentDTO.setTimestamp(doc.getDate());
        documentDTO.setTags(doc.getTags()
                               .stream()
                               .map(Tag::getTagName)
                               .collect(Collectors.toSet()));
        return documentDTO;
    }
}

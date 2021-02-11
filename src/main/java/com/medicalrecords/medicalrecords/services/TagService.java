package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.Documentation;
import com.medicalrecords.medicalrecords.entities.Tag;
import com.medicalrecords.medicalrecords.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService( TagRepository tagRepository ) {
        this.tagRepository = tagRepository;
    }

    public Set<Documentation> getDocumentsByTag( String tag ) {
        Tag documents = tagRepository.findByTagName(tag);
        return documents.getDocumentations();
    }
}

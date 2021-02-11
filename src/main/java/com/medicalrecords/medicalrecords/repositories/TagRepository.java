package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface TagRepository extends JpaRepository<Tag, String> {
    Tag findByTagName( String tag );
}

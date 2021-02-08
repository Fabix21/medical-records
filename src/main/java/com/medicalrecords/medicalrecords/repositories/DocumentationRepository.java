package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface DocumentationRepository extends JpaRepository<Documentation, String> {
}

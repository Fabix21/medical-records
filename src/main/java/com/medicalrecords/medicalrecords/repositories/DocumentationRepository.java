package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.Documentation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
public interface DocumentationRepository extends JpaRepository<Documentation, String> {
    List<Documentation> findByDateBetween( LocalDate from,LocalDate to );
}

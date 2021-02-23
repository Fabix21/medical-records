package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.entities.Patient;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface PatientRepository extends UserRepository<Patient> {

    Optional<Patient> findById( Long id );

}

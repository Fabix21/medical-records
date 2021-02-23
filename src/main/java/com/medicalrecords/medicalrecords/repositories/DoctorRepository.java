package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.Doctor;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface DoctorRepository extends UserRepository<Doctor> {

    Optional<Doctor> findById( Long id );

}

package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.Doctor;

import javax.transaction.Transactional;

@Transactional
public interface DoctorRepository extends UserRepository<Doctor> {
}

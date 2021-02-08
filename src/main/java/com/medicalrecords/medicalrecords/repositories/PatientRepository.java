package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.Patient;

import javax.transaction.Transactional;

@Transactional
public interface PatientRepository extends UserRepository<Patient> {
}

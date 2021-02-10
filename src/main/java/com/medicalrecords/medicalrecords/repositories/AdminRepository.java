package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.Admin;

import javax.transaction.Transactional;

@Transactional
public interface AdminRepository extends UserRepository<Admin> {
}

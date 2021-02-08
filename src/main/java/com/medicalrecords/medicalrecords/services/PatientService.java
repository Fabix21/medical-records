package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.exceptions.UsernameTakenException;
import com.medicalrecords.medicalrecords.repositories.DoctorRepository;
import com.medicalrecords.medicalrecords.repositories.PatientRepository;
import com.medicalrecords.medicalrecords.security.PatientPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PatientService extends UserService implements UserDetailsService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void save( Patient patient,String doctorName ) {
        boolean isPresent = patientRepository.existsByLogin(patient.getLogin());
        if (isPresent) {
            throw new UsernameTakenException();
        }
        final String password = patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        //final Doctor doctor = doctorRepository.findByLogin(doctorName).get();
        //Set<Doctor> doctors = new HashSet<>();
        // doctors.add(doctor);
        //patient.setDoctors(doctors);
        patient.setRole("PAC");
        patient.setPassword(password);
        patientRepository.save(patient);
    }

    public Page<Patient> findPaginated( int pageNum,int pageSize ) {
        return patientRepository.findAll(PageRequest.of(pageNum - 1,pageSize));
    }

    public Patient getPatient( String patientName ) {
        return getUser(patientName,patientRepository);
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        return new PatientPrincipal(patientRepository.findByLogin(username)
                                                     .orElseThrow(() -> new UsernameNotFoundException("User does not exist!")));
    }
}

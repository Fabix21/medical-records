package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.exceptions.UsernameTakenException;
import com.medicalrecords.medicalrecords.repositories.PatientRepository;
import com.medicalrecords.medicalrecords.security.Role;
import com.medicalrecords.medicalrecords.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientService extends UserService implements UserDetailsService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientService( PatientRepository patientRepository,PasswordEncoder passwordEncoder ) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "getAllPatients", allEntries = true)
    public void save( final Patient patient ) {
        boolean isPresent = patientRepository.existsByLogin(patient.getLogin());
        if (isPresent) {
            throw new UsernameTakenException();
        }
        final String password = patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patient.setRole(Role.PATIENT);
        patient.setPassword(password);
        patientRepository.save(patient);
    }

    public Patient getPatient( String patientName ) {
        return getUser(patientName,patientRepository);
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        return new UserPrincipal<>(patientRepository.findByLogin(username)
                                                    .orElseThrow(() -> new UsernameNotFoundException("Patient does not exist!")));
    }

    @Cacheable(value = "getAllPatients")
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
}

package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.exceptions.UsernameTakenException;
import com.medicalrecords.medicalrecords.repositories.DoctorRepository;
import com.medicalrecords.medicalrecords.security.DoctorPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class DoctorService extends UserService implements UserDetailsService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void save( Doctor doctor ) {
        boolean isPresent = doctorRepository.existsByLogin(doctor.getLogin());
        if (isPresent) {
            throw new UsernameTakenException();
        }
        final String password = doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setPassword(password);
        doctorRepository.save(doctor);
    }


    public String getDoctorTitle( String issuedBy ) {
        return getDoctorName(getDoctor(issuedBy));
    }

    private String getDoctorName( Doctor doctor ) {
        return "Dr. " + doctor.getName() + " " + doctor.getSurname();
    }

    public Doctor getDoctor( String doctorName ) {
        return getUser(doctorName,doctorRepository);
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        return new DoctorPrincipal(doctorRepository.findByLogin(username)
                                                   .orElseThrow(() -> new UsernameNotFoundException("User does not exist!")));
    }
}

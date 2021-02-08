/*
package com.medicalrecords.medicalrecords.services;
import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.entities.Patient;
import com.medicalrecords.medicalrecords.entities.User;
import com.medicalrecords.medicalrecords.exceptions.UsernameTakenException;
import com.medicalrecords.medicalrecords.repositories.UserRepository;
import com.medicalrecords.medicalrecords.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl( final UserRepository userRepository, PasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
       final User user =  userRepository.findByLogin(username)
                                        .orElseThrow(()-> new UsernameNotFoundException("User does not exist!"));
       return new UserPrincipal(user);
    }

    public void addPatient(final Patient patient, final String doctorName ) {
        boolean isPresent = userRepository.existsByLogin(patient.getLogin());
        if (isPresent) {
            throw new UsernameTakenException();
        }
        final String password = patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        final User doctor =  userRepository.findByLogin(doctorName).get();
        patient.getDoctors().add(doctor);
        patient.setPassword(password);
        userRepository.save(patient);
    }
    public void addDoctor(final Doctor doctor) {
        boolean isPresent = userRepository.existsByLogin(doctor.getLogin());
        if (isPresent) {
            throw new UsernameTakenException();
        }
        final String password = doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setPassword(password);
        userRepository.save(doctor);
    }

    public User getUser( String login ) {
        return userRepository.findByLogin(login)
                             .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));
    }

    public List<String> getAllUsersName() {
        return userRepository
                .findAll()
                .stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }
    public Page<Patient> findPaginated( int pageNum,int pageSize) {
        final Page<Patient> all = (Page<Patient>) userRepository.findAll(PageRequest.of(pageNum - 1,pageSize));

    }

    public String getDoctorTitle(String issuedBy){
        final Optional<String> doctorName = userRepository.findByLogin(issuedBy).map(this::getDoctorName);
        return doctorName.get();
    }

    private String getDoctorName(User doctor){
        return "Dr. " + doctor.getName() + " "  +  doctor.getSurname();
    }
}
*/

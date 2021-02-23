package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.dto.DoctorDTO;
import com.medicalrecords.medicalrecords.dto.DocumentDTO;
import com.medicalrecords.medicalrecords.entities.Doctor;
import com.medicalrecords.medicalrecords.exceptions.UsernameTakenException;
import com.medicalrecords.medicalrecords.mapper.DoctorMapper;
import com.medicalrecords.medicalrecords.repositories.DoctorRepository;
import com.medicalrecords.medicalrecords.security.Role;
import com.medicalrecords.medicalrecords.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DoctorService extends UserService implements UserDetailsService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorService( DoctorRepository doctorRepository,PasswordEncoder passwordEncoder ) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save( Doctor doctor ) {
        boolean isPresent = doctorRepository.existsByLogin(doctor.getLogin());
        if (isPresent) {
            throw new UsernameTakenException();
        }
        final String password = doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setPassword(password);
        doctor.setRole(Role.DOCTOR);
        doctorRepository.save(doctor);
    }

    public Doctor getDoctor( String doctorName ) {
        return getUser(doctorName,doctorRepository);
    }

    @Override
    public UserDetails loadUserByUsername( String username ) {
        return new UserPrincipal<>(doctorRepository.findByLogin(username)
                                                   .orElseThrow(() -> new UsernameNotFoundException("Doctor does not exist!")));
    }
    public DoctorDTO getDoctorDTOById( long id) {
        return doctorRepository.findById(id)
                               .map(DoctorMapper.MAPPER::doctorToDto)
                               .get();
    }
    public Doctor getDoctorById( long id) {
        return doctorRepository.findById(id).get();
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                               .stream()
                               .map(DoctorMapper.MAPPER::doctorToDto)
                               .collect(Collectors.toList());
    }
}

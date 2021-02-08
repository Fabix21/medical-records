package com.medicalrecords.medicalrecords.services;

import com.medicalrecords.medicalrecords.entities.User;
import com.medicalrecords.medicalrecords.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService {
    public <T extends User> T getUser( String login,UserRepository<T> userRepository ) {
        return userRepository.findByLogin(login)
                             .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));
    }
}

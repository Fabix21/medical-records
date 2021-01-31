package com.medicalrecords.medicalrecords.services;
import com.medicalrecords.medicalrecords.entities.User;
import com.medicalrecords.medicalrecords.exceptions.UsernameTakenException;
import com.medicalrecords.medicalrecords.repositories.UserRepository;
import com.medicalrecords.medicalrecords.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
       final User user =  userRepository.findByLogin(username)
                                        .orElseThrow(()-> new UsernameNotFoundException("User does not exist!"));
       return new UserPrincipal(user);
    }

    public void addUser(final User newUser ) {
        boolean isPresent = userRepository.existsByLogin(newUser.getLogin());
        if(isPresent) {
            throw new UsernameTakenException();
        }
        final String password = newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setPassword(password);
        userRepository.save(newUser);
    }
}

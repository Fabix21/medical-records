package com.medicalrecords.medicalrecords.configuration;

import com.medicalrecords.medicalrecords.entities.Admin;
import com.medicalrecords.medicalrecords.repositories.AdminRepository;
import com.medicalrecords.medicalrecords.security.Role;
import com.medicalrecords.medicalrecords.security.UserPrincipal;
import com.medicalrecords.medicalrecords.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class AdminConf extends UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminConf( PasswordEncoder passwordEncoder,AdminRepository adminRepository ) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        return new UserPrincipal<>(adminRepository.findByLogin(username)
                                                  .orElseThrow(() -> new UsernameNotFoundException("Admin does not exist!")));
    }

    @Component
    public class CommandLineAppStartupRunner implements CommandLineRunner {

        @Value("${admin.password}")
        private String password;

        @Override
        public void run( String[] arg0 ) throws Exception {
            if (!adminRepository.existsByLogin("admin")) {
                Admin admin = new Admin();
                admin.setLogin("admin");
                admin.setPassword(passwordEncoder.encode(password));
                admin.setRole(Role.ADMIN);
                adminRepository.save(admin);

            }
        }
    }
}

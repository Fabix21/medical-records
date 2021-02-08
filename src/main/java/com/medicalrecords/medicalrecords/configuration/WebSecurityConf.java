package com.medicalrecords.medicalrecords.configuration;

import com.medicalrecords.medicalrecords.services.DoctorService;
import com.medicalrecords.medicalrecords.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(doctorService);
        auth.userDetailsService(patientService);
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.httpBasic().and().csrf().disable().cors().disable().authorizeRequests()
            .antMatchers("/","/home","/js/**","/css/**").permitAll()
            .antMatchers("/addUser*").permitAll()
            .antMatchers("/uploadFile*").permitAll()
            .antMatchers("/getDocuments*").permitAll()
            .antMatchers("/getDocuments/**").permitAll()
            .antMatchers("/showUsers").permitAll()
            .antMatchers("/page*").permitAll()
            .antMatchers("/page/**").permitAll()
            .antMatchers("/addDoctor*").permitAll()
            .antMatchers("/addPatient*").permitAll()
            .antMatchers("/addDoc*").permitAll()
            .antMatchers("/patients*").permitAll()
            .anyRequest().authenticated().and().formLogin()
            .and().headers().frameOptions().sameOrigin();
    }
}


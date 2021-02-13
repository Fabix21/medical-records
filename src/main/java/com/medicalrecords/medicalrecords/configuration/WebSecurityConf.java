package com.medicalrecords.medicalrecords.configuration;

import com.medicalrecords.medicalrecords.services.DoctorService;
import com.medicalrecords.medicalrecords.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AdminConf adminConf;

    @Autowired
    public WebSecurityConf( DoctorService doctorService,
                            PatientService patientService,
                            AdminConf adminConf,
                            CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler ) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.adminConf = adminConf;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(doctorService);
        auth.userDetailsService(patientService);
        auth.userDetailsService(adminConf);
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.httpBasic().and().csrf().disable().cors().disable().authorizeRequests()
            .antMatchers("/js/**","/css/**").permitAll()
            .antMatchers("/swagger-ui*").permitAll()
            .antMatchers("/addDoctor*").hasRole("ADM")
            .antMatchers("/addPatient*").hasAnyRole("ADM", "DOC")
            .anyRequest().authenticated().and().formLogin().successHandler(customAuthenticationSuccessHandler);
        http.headers().frameOptions().sameOrigin();
    }
}


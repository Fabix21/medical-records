package com.medicalrecords.medicalrecords.configuration;

import com.medicalrecords.medicalrecords.services.UserService;
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
    private UserService userService;
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.httpBasic().and().csrf().disable().cors().disable().authorizeRequests()
            .antMatchers("/addUser*").permitAll()
            .antMatchers("/uploadFile*").permitAll()
            .anyRequest().authenticated().and().headers().frameOptions().sameOrigin();

    }
}


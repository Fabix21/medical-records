package com.medicalrecords.medicalrecords.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void onAuthenticationSuccess( HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Authentication authentication ) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_PAT")) {
            httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL("/checkDoc"));
        }
        if (roles.contains("ROLE_DOC")) {
            httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL("/patients"));
        }
        if (roles.contains("ROLE_ADM")) {
            httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL("/addDoctor"));
        }
    }
}



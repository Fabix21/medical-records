package com.medicalrecords.medicalrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MedicalRecordsApplication implements WebMvcConfigurer {

    public static void main( String[] args ) {
        SpringApplication.run(MedicalRecordsApplication.class,args);
    }

}

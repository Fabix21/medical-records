package com.medicalrecords.medicalrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class MedicalRecordsApplication {

    public static void main( String[] args ) {
        SpringApplication.run(MedicalRecordsApplication.class,args);
    }

}

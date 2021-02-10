package com.medicalrecords.medicalrecords.repositories;

import com.medicalrecords.medicalrecords.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, String> {
    Optional<T> findByLogin( final String login );

    boolean existsByLogin( final String login );
}

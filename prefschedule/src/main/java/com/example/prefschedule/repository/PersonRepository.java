package com.example.prefschedule.repository;

import com.example.prefschedule.model.AppUser;
import com.example.prefschedule.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<AppUser, Long> {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<Person> findByEmail(String email);
}
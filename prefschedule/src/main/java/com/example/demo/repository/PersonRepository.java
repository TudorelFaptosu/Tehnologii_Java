package com.example.demo.repository;

import com.example.demo.model.AppUser;
import com.example.demo.model.Instructor;
import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<AppUser, Long> {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<Person> findByEmail(String email);
}
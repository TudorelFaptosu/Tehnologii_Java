package com.example.demo.repository;

import com.example.demo.model.AppUser;
import com.example.demo.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<Instructor> findByEmail(String email);
}
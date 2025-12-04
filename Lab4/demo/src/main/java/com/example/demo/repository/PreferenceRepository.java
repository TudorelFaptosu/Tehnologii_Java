package com.example.demo.repository;

import com.example.demo.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    List<Preference> findByStudentId(Long studentId);
}
package com.example.demo.repository;

import com.example.demo.model.Instructor;
import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
    // 1. Derived Query
    List<Student> findByYear(Integer year);

    // 2. JPQL Query explicit
    @Query("SELECT s FROM Student s WHERE s.email LIKE %:domain")
    List<Student> findByEmailDomain(String domain);

    // 3. Transactional Modifying Query
    @Transactional
    @Modifying
    @Query("UPDATE Student s SET s.year = s.year + 1 WHERE s.year < :maxYear")
    void promoteStudents(Integer maxYear);
}
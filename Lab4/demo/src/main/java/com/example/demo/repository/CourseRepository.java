package com.example.demo.repository;

import com.example.demo.model.Course;
import com.example.demo.model.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, CourseCustomRepository {

    // Metode standar  d (Spring Data Magic)
    List<Course> findByType(CourseType type);

    Course findByCode(String code);
}
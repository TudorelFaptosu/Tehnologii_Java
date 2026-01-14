package com.example.demo.repository;

import com.example.demo.model.Course;
import com.example.demo.model.CourseRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRequirementRepository extends JpaRepository<CourseRequirement, Long> {
    List<CourseRequirement> findByCourse(Course course);
}
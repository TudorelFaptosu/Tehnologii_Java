package com.example.prefschedule.repository;

import com.example.prefschedule.model.Course;
import com.example.prefschedule.model.CourseRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRequirementRepository extends JpaRepository<CourseRequirement, Long> {
    List<CourseRequirement> findByCourse(Course course);
}
package com.example.demo.repository;

import com.example.demo.dto.CourseSearchCriteria;
import com.example.demo.model.Course;
import java.util.List;

public interface CourseCustomRepository {
    List<Course> findCoursesByCriteria(CourseSearchCriteria criteria);
}
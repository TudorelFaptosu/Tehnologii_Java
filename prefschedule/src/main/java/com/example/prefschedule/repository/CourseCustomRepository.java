package com.example.prefschedule.repository;

import com.example.prefschedule.dto.CourseSearchCriteria;
import com.example.prefschedule.model.Course;
import java.util.List;

public interface CourseCustomRepository {
    List<Course> findCoursesByCriteria(CourseSearchCriteria criteria);
}
package com.example.demo.services;

import com.example.demo.dto.CourseSearchCriteria;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Activam caching-ul. Rezultatul va fi stocat in memoria RAM.
    // Numele cache-ului este "course-search".
    // Daca metoda este apelata cu aceiasi parametri, nu se mai executa query-ul la baza de date.
    @Cacheable(value = "course-search")
    public List<Course> searchCourses(Integer year, Integer semester, String instructor) {
        // Simulam un mic delay (optional) ca sa vezi diferenta clar in teste
        // try { Thread.sleep(1000); } catch (InterruptedException e) {}

        CourseSearchCriteria criteria = new CourseSearchCriteria(year, semester, instructor);
        return courseRepository.findCoursesByCriteria(criteria);
    }
}
package com.example.prefschedule.controllers;

import com.example.prefschedule.model.Course;
import com.example.prefschedule.model.Student;
import com.example.prefschedule.patterns.cqrs.CourseCommandService;
import com.example.prefschedule.patterns.cqrs.CourseReadModel;
import com.example.prefschedule.patterns.cqrs.CourseReadRepository;
import com.example.prefschedule.patterns.es.GradeEventService;
import com.example.prefschedule.patterns.saga.RegistrationSagaOrchestrator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patterns")
public class PatternsController {

    private final RegistrationSagaOrchestrator sagaOrchestrator;
    private final GradeEventService eventService;
    private final CourseCommandService courseCommandService;
    private final CourseReadRepository courseReadRepository;

    public PatternsController(RegistrationSagaOrchestrator sagaOrchestrator,
                              GradeEventService eventService,
                              CourseCommandService courseCommandService,
                              CourseReadRepository courseReadRepository) {
        this.sagaOrchestrator = sagaOrchestrator;
        this.eventService = eventService;
        this.courseCommandService = courseCommandService;
        this.courseReadRepository = courseReadRepository;
    }

    // --- SAGA ---
    @PostMapping("/saga/register")
    public String testSaga(@RequestBody Student student) {
        try {
            sagaOrchestrator.registerStudentSaga(student);
            return "Saga Success";
        } catch (Exception e) {
            return "Saga Rolled Back: " + e.getMessage();
        }
    }

    // --- EVENT SOURCING ---
    @PostMapping("/es/grades")
    public String addGradeEvent(@RequestParam Long studentId, @RequestParam Long courseId, @RequestParam Double value) {
        eventService.addGradeEvent(studentId, courseId, value);
        return "Event stored";
    }

    @GetMapping("/es/grades")
    public Double getProjectedGrade(@RequestParam Long studentId, @RequestParam Long courseId) {
        return eventService.getCurrentGrade(studentId, courseId);
    }

    // --- CQRS ---
    @PostMapping("/cqrs/course")
    public String createCourseCQRS(@RequestBody Course course) {
        courseCommandService.createCourse(course);
        return "Written to Postgres and Synced to Mongo";
    }

    @GetMapping("/cqrs/courses")
    public List<CourseReadModel> searchCourses(@RequestParam String name) {
        // Reads specifically from MongoDB
        return courseReadRepository.findByNameContainingIgnoreCase(name);
    }
}
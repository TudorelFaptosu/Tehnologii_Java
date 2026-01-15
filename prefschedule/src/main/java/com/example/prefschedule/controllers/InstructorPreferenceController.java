package com.example.prefschedule.controllers;

import com.example.prefschedule.dto.RequirementDTO;
import com.example.prefschedule.model.Course;
import com.example.prefschedule.model.CourseRequirement;
import com.example.prefschedule.repository.CourseRepository;
import com.example.prefschedule.repository.CourseRequirementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors/preferences")
public class InstructorPreferenceController {

    private final CourseRequirementRepository reqRepository;
    private final CourseRepository courseRepository;

    public InstructorPreferenceController(CourseRequirementRepository reqRepository, CourseRepository courseRepository) {
        this.reqRepository = reqRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<?> addPreference(@PathVariable Long courseId, @RequestBody RequirementDTO reqDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CourseRequirement req = new CourseRequirement();
        req.setCourse(course);
        req.setCompulsoryAbbr(reqDto.getCompulsoryAbbr());
        req.setWeight(reqDto.getWeight());

        reqRepository.save(req);
        return ResponseEntity.ok("Requirement added");
    }
}
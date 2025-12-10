package com.example.demo.services;

import com.example.demo.model.Course;
import com.example.demo.model.Preference;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.PreferenceRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public PreferenceService(PreferenceRepository preferenceRepository, 
                             StudentRepository studentRepository, 
                             CourseRepository courseRepository) {
        this.preferenceRepository = preferenceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Preference> getPreferencesByStudentId(Long studentId) {
        return preferenceRepository.findByStudentId(studentId);
    }

    public Preference savePreference(Long studentId, Long courseId, Integer rank) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        // Check if preference already exists
        // Note: Ideally we should have a method in repository to check existence or handle DataIntegrityViolationException
        // For simplicity, we assume the unique constraint in DB handles duplicates or we check here if needed.
        
        Preference preference = new Preference(student, course, rank);
        return preferenceRepository.save(preference);
    }
}

package com.example.demo.services;

import com.example.demo.model.Instructor;
import com.example.demo.repository.InstructorRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
    }

    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public Instructor updateInstructor(Long id, Instructor instructorDetails) {
        Instructor instructor = getInstructorById(id);
        
        instructor.setName(instructorDetails.getName());
        instructor.setEmail(instructorDetails.getEmail());
        
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(Long id) {
        Instructor instructor = getInstructorById(id);
        instructorRepository.delete(instructor);
    }
}

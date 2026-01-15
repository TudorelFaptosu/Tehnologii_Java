package com.example.prefschedule.patterns.saga;

import com.example.prefschedule.model.Student;
import com.example.prefschedule.repository.StudentRepository;
import com.example.prefschedule.services.StudentService;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegistrationSagaOrchestrator {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSagaOrchestrator.class);

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public RegistrationSagaOrchestrator(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    public void registerStudentSaga(Student student) {
        logger.info("SAGA START: Registering student {}", student.getName());
        Student savedStudent = null;

        try {
            // STEP 1: Create Student (Retriable)
            savedStudent = createStudentStep(student);

            // STEP 2: Assign Welcome Pack (Simulating logic that might fail)
            assignWelcomePackStep(savedStudent);

            logger.info("SAGA COMPLETED: Student registered successfully.");

        } catch (Exception e) {
            logger.error("SAGA FAILED: Initiating Compensation. Reason: {}", e.getMessage());
            // COMPENSATION
            if (savedStudent != null) {
                compensateCreateStudent(savedStudent);
            }
            throw e;
        }
    }

    @Retry(name = "sagaRetry")
    public Student createStudentStep(Student student) {
        logger.info("STEP 1: Saving student...");
        return studentService.save(student); // Assume this saves to Postgres
    }

    public void assignWelcomePackStep(Student student) {
        logger.info("STEP 2: Assigning welcome pack...");
        // Simulation: Fail if name contains "Fail"
        if (student.getName().contains("Fail")) {
            throw new RuntimeException("Could not assign welcome pack (Simulated Error)");
        }
        // Logic to assign pack would go here
    }

    // COMPENSATION TRANSACTION
    public void compensateCreateStudent(Student student) {
        logger.info("COMPENSATION: Deleting student ID {}", student.getId());
        studentRepository.deleteById(student.getId());
    }
}
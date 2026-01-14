package com.example.gradeenricher.service;

import com.example.quickgrade.dto.GradeEvent; // Asigura-te ca ai clasa DTO si aici
import com.example.gradeenricher.dto.StudentEnrichedEvent;
import com.example.gradeenricher.dto.FinalGradeEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PipelineService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Mock Data Bases pentru viteza demonstratiei
    private static final Map<String, String> STUDENT_NAMES = Map.of("S100", "Student Exemplu", "S101", "Ion Popescu");
    private static final Map<String, Integer> STUDENT_YEARS = Map.of("S100", 3, "S101", 2);
    private static final Map<String, String> COURSE_NAMES = Map.of("CS301", "Tehnologii Java", "CS302", "AI");
    private static final Map<String, Integer> COURSE_SEMESTERS = Map.of("CS301", 1, "CS302", 1);

    public PipelineService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // --- COMPONENT 2: Adauga Student Name & Year ---
    @KafkaListener(topics = "raw-grades", groupId = "enricher-group-student", concurrency = "3")
    public void enrichWithStudent(GradeEvent event) {
        System.out.println("[1] Processing RAW: " + event.getStudentCode());

        String name = STUDENT_NAMES.getOrDefault(event.getStudentCode(), "Unknown Student");
        Integer year = STUDENT_YEARS.getOrDefault(event.getStudentCode(), 1);

        StudentEnrichedEvent enrichedEvent = new StudentEnrichedEvent(
                event.getStudentCode(), name, year,
                event.getCourseCode(), event.getValue()
        );

        // Trimite catre urmatorul topic
        kafkaTemplate.send("student-enriched-grades", enrichedEvent.getStudentCode(), enrichedEvent);
    }

    // --- COMPONENT 3: Adauga Course Name & Semester ---
    @KafkaListener(topics = "student-enriched-grades", groupId = "enricher-group-course", concurrency = "3")
    public void enrichWithCourse(StudentEnrichedEvent event) {
        System.out.println("[2] Processing PARTIAL: " + event.getStudentName());

        String courseName = COURSE_NAMES.getOrDefault(event.getCourseCode(), "Unknown Course");
        Integer semester = COURSE_SEMESTERS.getOrDefault(event.getCourseCode(), 0);

        FinalGradeEvent finalEvent = new FinalGradeEvent(
                event.getStudentCode(), event.getStudentName(), event.getStudentYear(),
                event.getCourseCode(), courseName, semester,
                event.getValue()
        );

        // Trimite catre topicul final consumat de PrefSchedule
        kafkaTemplate.send("final-grades", finalEvent.getStudentCode(), finalEvent);
    }
}
package com.example.gradeenricher.service; // <--- ATENTIE LA PACHET

// Asigura-te ca ai clasele DTO create in pachetul com.example.gradeenricher.dto
import com.example.gradeenricher.dto.GradeEvent;
import com.example.gradeenricher.dto.StudentEnrichedEvent;
import com.example.gradeenricher.dto.FinalGradeEvent;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PipelineService {

    // Aici injectam exact ce am definit in KafkaConfig: <String, Object>
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Date hardcodate (Mock)
    private static final Map<String, String> STUDENT_NAMES = Map.of("S100", "Student Exemplu", "S101", "Ion Popescu");
    private static final Map<String, Integer> STUDENT_YEARS = Map.of("S100", 3, "S101", 2);
    private static final Map<String, String> COURSE_NAMES = Map.of("CS301", "Tehnologii Java", "CS302", "AI");
    private static final Map<String, Integer> COURSE_SEMESTERS = Map.of("CS301", 1, "CS302", 1);

    public PipelineService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Consumer 1: Primeste Raw, adauga Student info -> Trimite Partial
    @KafkaListener(topics = "raw-grades", groupId = "enricher-group-student", concurrency = "3")
    public void enrichWithStudent(GradeEvent event) {
        System.out.println("[1] Processing RAW: " + event.getStudentCode());

        String name = STUDENT_NAMES.getOrDefault(event.getStudentCode(), "Unknown Student");
        Integer year = STUDENT_YEARS.getOrDefault(event.getStudentCode(), 1);

        StudentEnrichedEvent enrichedEvent = new StudentEnrichedEvent(
                event.getStudentCode(), name, year,
                event.getCourseCode(), event.getValue()
        );

        kafkaTemplate.send("student-enriched-grades", enrichedEvent.getStudentCode(), enrichedEvent);
    }

    // Consumer 2: Primeste Partial, adauga Curs info -> Trimite Final
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

        kafkaTemplate.send("final-grades", finalEvent.getStudentCode(), finalEvent);
    }
}
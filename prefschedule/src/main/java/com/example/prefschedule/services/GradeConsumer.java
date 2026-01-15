package com.example.prefschedule.services;

import com.example.prefschedule.dto.FinalGradeEvent;
import com.example.prefschedule.dto.GradeEvent; // Import necesar pentru CSV
import com.example.prefschedule.model.Course;
import com.example.prefschedule.model.Grade;
import com.example.prefschedule.repository.CourseRepository;
import com.example.prefschedule.repository.GradeRepository;
import com.example.prefschedule.repository.StudentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class GradeConsumer {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;

    // Metrics
    private final AtomicLong processedCount = new AtomicLong(0);
    private final long startTime = System.currentTimeMillis();

    public GradeConsumer(StudentRepository studentRepository, CourseRepository courseRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
    }

    // --- METODA 1: PENTRU PIPELINE (Kafka Listener - Advanced) ---
    // Asculta topicul "final-grades" unde ajung datele procesate complet
    @KafkaListener(topics = "final-grades", groupId = "pref-schedule-group", concurrency = "3")
    public void consumeFinalGrade(FinalGradeEvent event) {
        long currentCount = processedCount.incrementAndGet();

        // Metrics logic
        if (currentCount % 10 == 0) {
            long duration = System.currentTimeMillis() - startTime;
            double throughput = (double) currentCount / (duration / 1000.0);
            System.out.printf("--- METRICS: Processed %d messages. Throughput: %.2f msg/sec ---%n", currentCount, throughput);
        }

        System.out.println("[3] Consuming FINAL via Kafka: " + event);

        // Salvare in DB folosind datele gata procesate
        saveGradeToDb(event.getStudentCode(), event.getCourseCode(), event.getValue());
    }

    // --- METODA 2: PENTRU CONTROLLER (CSV Upload - Homework) ---
    // Aceasta metoda este apelata direct de GradeController, nu prin Kafka
    public void consumeGrade(GradeEvent event) {
        System.out.println("Processing DIRECT/CSV grade: " + event);
        saveGradeToDb(event.getStudentCode(), event.getCourseCode(), event.getValue());
    }

    // Metoda helper pentru a evita duplicarea codului de salvare
    private void saveGradeToDb(String studentCode, String courseCode, Double value) {
        studentRepository.findByCode(studentCode).ifPresent(student -> {
            Course course = courseRepository.findByCode(courseCode);
            if (course != null) {
                Grade grade = new Grade(student, course, value);
                gradeRepository.save(grade);
                System.out.println("Saved grade to database.");
            } else {
                System.err.println("Course not found: " + courseCode);
            }
        });
    }
}
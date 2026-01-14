package com.example.demo.services;

import com.example.demo.dto.FinalGradeEvent;
import com.example.demo.model.Course;
import com.example.demo.model.Grade;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.GradeRepository;
import com.example.demo.repository.StudentRepository;
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

    // Asculta topicul FINAL din pipeline. Concurrency = 3 pentru viteza.
    @KafkaListener(topics = "final-grades", groupId = "pref-schedule-group", concurrency = "3")
    public void consumeFinalGrade(FinalGradeEvent event) {
        long currentCount = processedCount.incrementAndGet();

        // Logica de throughput (afisam la fiecare 10 mesaje)
        if (currentCount % 10 == 0) {
            long duration = System.currentTimeMillis() - startTime;
            double throughput = (double) currentCount / (duration / 1000.0);
            System.out.printf("--- METRICS: Processed %d messages. Throughput: %.2f msg/sec ---%n", currentCount, throughput);
        }

        System.out.println("[3] Consuming FINAL: " + event);

        // Salvare in DB (adaptat la datele primite, sau facem lookup daca vrem consistenta cu DB locala)
        // Deoarece am primit deja numele si cursul in eveniment, am putea sa le salvam direct,
        // dar pentru consistenta JPA vom cauta entitatile existente.

        studentRepository.findByCode(event.getStudentCode()).ifPresent(student -> {
            Course course = courseRepository.findByCode(event.getCourseCode());
            if (course != null) {
                // Salvam doar daca nu exista deja (simplificare)
                Grade grade = new Grade(student, course, event.getValue());
                gradeRepository.save(grade);
            }
        });
    }
}
package com.example.quickgrade.controller;

import com.example.quickgrade.dto.GradeEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final KafkaTemplate<String, GradeEvent> kafkaTemplate;

    public GradeController(KafkaTemplate<String, GradeEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> publishGrade(@RequestBody GradeEvent event) {
        // Folosim cheia pentru partiționare corectă (toate notele aceluiași student ajung în aceeași partiție)
        // Trimitem catre topicul de start al pipeline-ului: raw-grades
        kafkaTemplate.send("raw-grades", event.getStudentCode(), event);
        return ResponseEntity.ok("Grade published to Pipeline (raw-grades)");
    }
}
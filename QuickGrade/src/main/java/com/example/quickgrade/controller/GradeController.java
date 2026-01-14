package com.example.quickgrade.controller;

import com.example.quickgrade.dto.GradeEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    // MODIFICARE: Folosim Object in loc de GradeEvent pentru a permite injectia automata
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public GradeController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> publishGrade(@RequestBody GradeEvent event) {
        // Trimitem catre topicul de start al pipeline-ului
        kafkaTemplate.send("raw-grades", event.getStudentCode(), event);
        return ResponseEntity.ok("Grade published to Pipeline (raw-grades)");
    }
}
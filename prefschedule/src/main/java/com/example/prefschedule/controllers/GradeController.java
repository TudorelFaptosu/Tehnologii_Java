package com.example.prefschedule.controllers;

import com.example.prefschedule.dto.GradeEvent;
import com.example.prefschedule.model.Grade;
import com.example.prefschedule.repository.GradeRepository;
import com.example.prefschedule.services.GradeConsumer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeRepository gradeRepository;
    private final GradeConsumer gradeConsumer; // Reutilizam logica din consumer

    public GradeController(GradeRepository gradeRepository, GradeConsumer gradeConsumer) {
        this.gradeRepository = gradeRepository;
        this.gradeConsumer = gradeConsumer;
    }

    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadGradesCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int count = 0;
            // Presupunem format CSV: studentCode,courseCode,grade
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    GradeEvent event = new GradeEvent();
                    event.setStudentCode(parts[0].trim());
                    event.setCourseCode(parts[1].trim());
                    event.setValue(Double.parseDouble(parts[2].trim()));

                    // Procesam direct folosind logica existenta
                    // (Nota: Intr-un scenariu real am putea publica inapoi in Kafka,
                    // dar cerinta cere "loading", deci procesam direct)
                    try {
                        gradeConsumer.consumeGrade(event);
                        count++;
                    } catch (Exception e) {
                        System.err.println("Error processing CSV line: " + line + " -> " + e.getMessage());
                    }
                }
            }
            return ResponseEntity.ok("Processed " + count + " grades from CSV.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error parsing CSV: " + e.getMessage());
        }
    }
}
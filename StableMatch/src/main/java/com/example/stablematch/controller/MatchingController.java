package com.example.stablematch.controller;

import com.example.stablematch.dto.MatchingProblem;
import com.example.stablematch.dto.Solution;
import com.example.stablematch.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @PostMapping("/solve")
    public ResponseEntity<Solution> solveProblem(@RequestBody MatchingProblem problem) {
        Solution solution = matchingService.solveRandom(problem);
        return ResponseEntity.ok(solution);
    }

    @GetMapping("/assignments")
    public ResponseEntity<Solution> getLatestAssignments() {
        Solution solution = matchingService.getLatestSolution();
        if (solution == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(solution);
    }

    // Exemplu endpoint: Get assignments for a specific student (din ultima soluție)
    @GetMapping("/assignments/student/{studentId}")
    public ResponseEntity<Long> getCourseForStudent(@PathVariable Long studentId) {
        Solution solution = matchingService.getLatestSolution();
        if (solution == null) return ResponseEntity.notFound().build();

        for (var entry : solution.getAssignments().entrySet()) {
            if (entry.getValue().contains(studentId)) {
                return ResponseEntity.ok(entry.getKey()); // Returnează ID-ul cursului
            }
        }
        return ResponseEntity.notFound().build();
    }
}
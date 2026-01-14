package com.example.demo.controllers;

import com.example.demo.dto.MatchingProblem;
import com.example.demo.dto.Solution;
import com.example.demo.services.MatchingIntegrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/allocations")
public class AllocationController {

    private final MatchingIntegrationService integrationService;

    public AllocationController(MatchingIntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @PostMapping("/{packId}")
    public ResponseEntity<?> startAllocation(@PathVariable Long packId) throws ExecutionException, InterruptedException {
        // 1. Construiește problema
        MatchingProblem problem = integrationService.buildProblemForPack(packId);

        // 2. Apelează serviciul extern cu reziliență
        Solution solution = integrationService.solveProblemAsync(problem).get();

        // 3. (Opțional) Salvează rezultatul 'solution' în baza de date locală PrefSchedule
        // ... cod salvare ...

        return ResponseEntity.ok(solution);
    }
}
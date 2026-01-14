package com.example.demo.services;

import com.example.demo.dto.MatchingProblem;
import com.example.demo.dto.Solution;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;

@Service
public class MatchingIntegrationService {

    // ... repository injections ...
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String STABLE_MATCH_URL = "http://localhost:8081/api/matching/solve";

    // ... metoda buildProblemForPack ...

    // --- METODA REZILIENTĂ ---

    @CircuitBreaker(name = "stableMatchService", fallbackMethod = "fallbackCircuitBreaker")
    @RateLimiter(name = "stableMatchService", fallbackMethod = "fallbackRateLimiter")
    @Bulkhead(name = "stableMatchService", fallbackMethod = "fallbackBulkhead")
    @Retry(name = "stableMatchService")
    public Solution solveProblem(MatchingProblem problem) {
        // Simulăm un mic delay pentru a testa Bulkhead-ul (concurența)
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        return restTemplate.postForObject(STABLE_MATCH_URL, problem, Solution.class);
    }

    // --- FALLBACK METHODS ---
    // E important să aibă semnătura: (ArgumenteMetodaOriginala, Throwable)

    public Solution fallbackCircuitBreaker(MatchingProblem problem, Throwable t) {
        System.out.println(">>> CIRCUIT BREAKER OPEN or ERROR: " + t.getMessage());
        return new Solution(new HashMap<>()); // Returnăm empty map
    }

    public Solution fallbackRateLimiter(MatchingProblem problem, Throwable t) {
        System.out.println(">>> RATE LIMIT EXCEEDED: Too many requests!");
        return new Solution(new HashMap<>());
    }

    public Solution fallbackBulkhead(MatchingProblem problem, Throwable t) {
        System.out.println(">>> BULKHEAD FULL: Too many concurrent calls!");
        return new Solution(new HashMap<>());
    }
}
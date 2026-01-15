package com.example.prefschedule.services;

import com.example.prefschedule.dto.CourseOption;
import com.example.prefschedule.dto.MatchingProblem;
import com.example.prefschedule.dto.Solution;
import com.example.prefschedule.dto.StudentCandidate;
import com.example.prefschedule.model.Course;
import com.example.prefschedule.model.Preference;
import com.example.prefschedule.model.Student;
import com.example.prefschedule.repository.CourseRepository;
import com.example.prefschedule.repository.PreferenceRepository;
import com.example.prefschedule.repository.StudentRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MatchingIntegrationService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final PreferenceRepository preferenceRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String STABLE_MATCH_URL = "http://localhost:8081/api/matching/solve";

    public MatchingIntegrationService(CourseRepository courseRepository,
                                      StudentRepository studentRepository,
                                      PreferenceRepository preferenceRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.preferenceRepository = preferenceRepository;
    }

    public MatchingProblem buildProblemForPack(Long packId) {
        MatchingProblem problem = new MatchingProblem();

        // 1. Luam cursurile din Pack
        List<Course> dbCourses = courseRepository.findAll().stream()
                .filter(c -> c.getPack() != null && c.getPack().getId().equals(packId))
                .collect(Collectors.toList());

        List<CourseOption> courseOptions = new ArrayList<>();
        for (Course c : dbCourses) {
            CourseOption co = new CourseOption();
            co.setId(c.getId());
            // Folosim getMaxStudents() (din Course.java)
            co.setCapacity(c.getMaxStudents());
            courseOptions.add(co);
        }
        problem.setCourses(courseOptions);

        // 2. Luam studentii si preferintele lor
        List<Student> dbStudents = studentRepository.findAll();
        List<StudentCandidate> candidates = new ArrayList<>();

        for (Student s : dbStudents) {
            // Luam preferintele studentului pentru cursurile din acest pack
            List<Preference> prefs = preferenceRepository.findAll().stream()
                    .filter(p -> p.getStudent().getId().equals(s.getId()))
                    .filter(p -> dbCourses.stream().anyMatch(c -> c.getId().equals(p.getCourse().getId())))

                    // --- FIX: Folosim getRank() in loc de getPriority() ---
                    .sorted(Comparator.comparingInt(Preference::getRank))

                    .collect(Collectors.toList());

            if (!prefs.isEmpty()) {
                StudentCandidate sc = new StudentCandidate();
                sc.setId(s.getId());

                List<Long> courseIds = prefs.stream()
                        .map(p -> p.getCourse().getId())
                        .collect(Collectors.toList());

                // Folosim setPreferredCourseIds (din StudentCandidate.java)
                sc.setPreferredCourseIds(courseIds);

                candidates.add(sc);
            }
        }
        problem.setStudents(candidates);

        return problem;
    }

    // --- METODELE REZILIENTE ---

    @CircuitBreaker(name = "stableMatchService", fallbackMethod = "fallbackCircuitBreaker")
    @RateLimiter(name = "stableMatchService", fallbackMethod = "fallbackRateLimiter")
    @Bulkhead(name = "stableMatchService", fallbackMethod = "fallbackBulkhead")
    @Retry(name = "stableMatchService")
    public Solution solveProblem(MatchingProblem problem) {
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        return restTemplate.postForObject(STABLE_MATCH_URL, problem, Solution.class);
    }

    public CompletableFuture<Solution> solveProblemAsync(MatchingProblem problem) {
        return CompletableFuture.supplyAsync(() -> solveProblem(problem));
    }

    // --- FALLBACKS ---
    public Solution fallbackCircuitBreaker(MatchingProblem problem, Throwable t) {
        System.out.println(">>> CIRCUIT BREAKER OPEN or ERROR: " + t.getMessage());
        return new Solution(new HashMap<>());
    }

    public Solution fallbackRateLimiter(MatchingProblem problem, Throwable t) {
        System.out.println(">>> RATE LIMIT EXCEEDED");
        return new Solution(new HashMap<>());
    }

    public Solution fallbackBulkhead(MatchingProblem problem, Throwable t) {
        System.out.println(">>> BULKHEAD FULL");
        return new Solution(new HashMap<>());
    }
}
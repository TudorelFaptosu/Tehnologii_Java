package com.example.demo.services;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MatchingIntegrationService {

    @Autowired private StudentRepository studentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private GradeRepository gradeRepository;
    @Autowired private PreferenceRepository preferenceRepository;
    @Autowired private CourseRequirementRepository reqRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String STABLE_MATCH_URL = "http://localhost:8081/api/matching/solve";

    // --- LOGICA DE CONSTRUIRE A PROBLEMEI ---

    public MatchingProblem buildProblemForPack(Long packId) {
        // Obținem studenții de anul 3 (exemplu hardcodat sau filtrare după pack)
        // În realitate ar trebui să filtrezi studenții care aparțin anului pachetului
        List<Student> students = studentRepository.findAll().stream()
                .filter(s -> s.getYear() == 3)
                .collect(Collectors.toList());

        List<Course> courses = courseRepository.findByPackId(packId);

        MatchingProblem problem = new MatchingProblem();

        // 1. Mapare Studenți și Preferințe
        List<StudentCandidate> studentCandidates = new ArrayList<>();
        for (Student s : students) {
            StudentCandidate sc = new StudentCandidate();
            sc.setId(s.getId());
            sc.setName(s.getName());

            // Luăm preferințele doar pentru cursurile din acest pachet
            List<Long> prefs = preferenceRepository.findByStudentId(s.getId()).stream()
                    .filter(p -> p.getCourse().getPack().getId().equals(packId))
                    .sorted(Comparator.comparingInt(Preference::getRank))
                    .map(p -> p.getCourse().getId())
                    .collect(Collectors.toList());

            sc.setPreferredCourseIds(prefs);
            studentCandidates.add(sc);
        }
        problem.setStudents(studentCandidates);

        // 2. Mapare Cursuri și Ierarhia Studenților (Weighted Average)
        List<CourseOption> courseOptions = new ArrayList<>();
        for (Course c : courses) {
            CourseOption co = new CourseOption();
            co.setId(c.getId());
            co.setCapacity(c.getMaxStudents()); // Asigură-te că ai maxStudents în Course

            // Sortăm studenții pe baza mediei ponderate
            List<Long> rankedStudents = students.stream()
                    .sorted((s1, s2) -> Double.compare(calculateWeightedScore(s2, c), calculateWeightedScore(s1, c))) // Descrescător
                    .map(Student::getId)
                    .collect(Collectors.toList());

            co.setPreferredStudentIds(rankedStudents);
            courseOptions.add(co);
        }
        problem.setCourses(courseOptions);

        return problem;
    }

    private double calculateWeightedScore(Student student, Course course) {
        List<CourseRequirement> requirements = reqRepository.findByCourse(course);

        // Dacă nu există cerințe, toți studenții sunt egali (sau media generală - simplificăm la 0)
        if (requirements.isEmpty()) return 0.0;

        double totalWeight = 0.0;
        double weightedSum = 0.0;

        for (CourseRequirement req : requirements) {
            // Căutăm nota studentului la materia obligatorie cerută
            // Această metodă trebuie să existe în GradeRepository sau o simulăm aici
            // Grade grade = gradeRepository.findByStudentAndCourseAbbr(student, req.getCompulsoryAbbr());

            // Simulăm căutarea notei (ar trebui un JOIN complex sau parcurgere)
            // Presupunem că Grade are referință la Course și Course are abbr
            Double gradeVal = gradeRepository.findAll().stream() // Ineficient, dar demonstrativ
                    .filter(g -> g.getStudent().getId().equals(student.getId()) &&
                            g.getCourse().getAbbr().equalsIgnoreCase(req.getCompulsoryAbbr()))
                    .map(Grade::getValue)
                    .findFirst()
                    .orElse(0.0);

            weightedSum += gradeVal * req.getWeight();
            totalWeight += req.getWeight();
        }

        return (totalWeight == 0) ? 0.0 : weightedSum / totalWeight;
    }

    // --- REZILIENȚĂ ---

    @Retry(name = "stableMatchService", fallbackMethod = "fallbackMatching")
    @CircuitBreaker(name = "stableMatchService", fallbackMethod = "fallbackMatching")
    @TimeLimiter(name = "stableMatchService")
    public CompletableFuture<Solution> solveProblemAsync(MatchingProblem problem) {
        return CompletableFuture.supplyAsync(() ->
                restTemplate.postForObject(STABLE_MATCH_URL, problem, Solution.class)
        );
    }

    // Fallback Method
    public CompletableFuture<Solution> fallbackMatching(MatchingProblem problem, Throwable t) {
        System.err.println("StableMatch service unavailable: " + t.getMessage());
        // Returnăm o soluție goală sau o eroare controlată
        return CompletableFuture.completedFuture(new Solution(new HashMap<>()));
    }
}
package com.example.stablematch.service;

import com.example.stablematch.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchingService {

    // Stocare în memorie pentru a răspunde la GET requests (conform cerinței Homework)
    private final Map<String, Solution> solutionHistory = new ConcurrentHashMap<>();

    public Solution solveRandom(MatchingProblem problem) {
        Map<Long, List<Long>> assignments = new HashMap<>();

        // Inițializare liste cursuri
        for (CourseOption course : problem.getCourses()) {
            assignments.put(course.getId(), new ArrayList<>());
        }

        // Copiem studenții pentru a nu modifica inputul original
        List<StudentCandidate> unassignedStudents = new ArrayList<>(problem.getStudents());

        // Algoritm Random: Amestecăm studenții
        Collections.shuffle(unassignedStudents);

        for (StudentCandidate student : unassignedStudents) {
            boolean assigned = false;
            // Iterăm prin preferințele studentului
            for (Long courseId : student.getPreferredCourseIds()) {
                CourseOption course = findCourseById(problem.getCourses(), courseId);

                if (course != null) {
                    List<Long> currentEnrolled = assignments.get(courseId);
                    // Verificăm capacitatea
                    if (currentEnrolled.size() < course.getCapacity()) {
                        currentEnrolled.add(student.getId());
                        assigned = true;
                        break; // Studentul a fost alocat, trecem la următorul
                    }
                }
            }
            // Dacă studentul nu prinde loc la preferințe, în acest algoritm simplu rămâne nealocat
            // sau ar putea fi pus la un curs random cu locuri libere (opțional)
        }

        Solution solution = new Solution(assignments);
        // Salvăm soluția în istoric (cheia poate fi un ID generat sau timestamp, simplificăm aici)
        solutionHistory.put("latest", solution);

        return solution;
    }

    public Solution getLatestSolution() {
        return solutionHistory.get("latest");
    }

    private CourseOption findCourseById(List<CourseOption> courses, Long id) {
        return courses.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }
}
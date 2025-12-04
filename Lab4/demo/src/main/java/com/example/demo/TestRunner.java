package com.example.demo;

import com.example.demo.services.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    private final CourseService courseService;

    public TestRunner(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- START CACHE TEST ---");

        long start = System.currentTimeMillis();
        // Prima apelare: Merge la baza de date
        System.out.println("Rezultate 1: " + courseService.searchCourses(3, 1, "M").size());
        long end = System.currentTimeMillis();
        System.out.println("Timp executie 1 (Fara Cache): " + (end - start) + " ms");

        start = System.currentTimeMillis();
        // A doua apelare: Ar trebui sa vina INSTANT din memorie
        System.out.println("Rezultate 2: " + courseService.searchCourses(3, 1, "M").size());
        end = System.currentTimeMillis();
        System.out.println("Timp executie 2 (Cu Cache): " + (end - start) + " ms");

        System.out.println("--- END CACHE TEST ---");
    }
}
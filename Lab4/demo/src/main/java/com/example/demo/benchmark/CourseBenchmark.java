package com.example.demo.benchmark;

import com.example.demo.DemoApplication; // Importa clasa ta Main
import com.example.demo.services.CourseService;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

// Configurare JMH
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime) // Masuram timpul mediu de executie
@OutputTimeUnit(TimeUnit.MILLISECONDS) // Rezultatul in milisecunde
public class CourseBenchmark {

    private ConfigurableApplicationContext context;
    private CourseService courseService;

    // Aceasta metoda ruleaza O SINGURA DATA inainte de teste pentru a porni Spring
    @Setup
    public void setup() {
        // Pornim aplicatia Spring manual pentru a avea acces la baza de date
        context = SpringApplication.run(DemoApplication.class);
        // Luam bean-ul de Service din context
        courseService = context.getBean(CourseService.class);
    }

    // Aceasta metoda ruleaza DUPA teste pentru a inchide Spring
    @TearDown
    public void tearDown() {
        context.close();
    }

    // TESTUL PROPRIU-ZIS
    // JMH va rula metoda asta de multe ori
    @Benchmark
    public void testSearchWithCache() {
        // Cautam ceva ce stim ca exista.
        // Prima rulare va fi lenta (DB), urmatoarele vor fi instantanee (Cache)
        courseService.searchCourses(3, 1, "Popescu");
    }

    // Metoda MAIN ca sa poti da Run direct din IntelliJ/Eclipse la acest fisier
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
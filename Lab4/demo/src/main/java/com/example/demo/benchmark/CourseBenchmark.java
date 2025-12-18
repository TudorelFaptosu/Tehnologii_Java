package com.example.demo.benchmark;

import com.example.demo.DemoApplication;
import com.example.demo.services.CourseService;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

// Configurare JMH
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CourseBenchmark {

    private ConfigurableApplicationContext context;
    private CourseService courseService;


    @Setup
    public void setup() {

        context = SpringApplication.run(DemoApplication.class);
        courseService = context.getBean(CourseService.class);
    }

    @TearDown
    public void tearDown() {
        context.close();
    }


    @Benchmark
    public void testSearchWithCache() {

        courseService.searchCourses(3, 1, "Popescu");
    }


    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
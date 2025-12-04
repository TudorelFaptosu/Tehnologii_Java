package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packs")
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "study_year", nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer semester;

    @Column(nullable = false)
    private String name;

    // Relatie OneToMany catre Course
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    // --- Constructori ---

    public Pack() {
    }

    public Pack(Integer year, Integer semester, String name) {
        this.year = year;
        this.semester = semester;
        this.name = name;
    }

    // --- Getters si Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    // Metoda helper
    public void addCourse(Course course) {
        courses.add(course);
        course.setPack(this);
    }

    @Override
    public String toString() {
        return "Pack{id=" + id + ", name='" + name + "', year=" + year + "}";
    }
}
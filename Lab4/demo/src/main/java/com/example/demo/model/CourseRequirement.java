package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_requirements")
public class CourseRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "compulsory_abbr", nullable = false)
    private String compulsoryAbbr;

    @Column(nullable = false)
    private Double weight;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public String getCompulsoryAbbr() { return compulsoryAbbr; }
    public void setCompulsoryAbbr(String compulsoryAbbr) { this.compulsoryAbbr = compulsoryAbbr; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}
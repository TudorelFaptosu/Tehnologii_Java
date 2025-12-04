package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseType type;

    @Column(nullable = false, unique = true)
    private String code;

    private String abbr;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "group_count", columnDefinition = "integer default 1")
    private Integer groupCount = 1;

    // Relatii ManyToOne

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pack_id")
    private Pack pack;

    // --- Constructori ---

    public Course() {
    }

    public Course(CourseType type, String code, String name, Instructor instructor, Pack pack) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.instructor = instructor;
        this.pack = pack;
    }

    // --- Getters si Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    @Override
    public String toString() {
        return "Course{id=" + id + ", code='" + code + "', name='" + name + "'}";
    }
}
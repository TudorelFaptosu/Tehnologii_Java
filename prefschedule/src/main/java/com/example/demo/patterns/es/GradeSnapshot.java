package com.example.demo.patterns.es;

import jakarta.persistence.*;


@Entity

public class GradeSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Long courseId;
    private Double currentGrade;
    private Long lastEventId; // To know where to resume replaying

    public GradeSnapshot() {}
    public GradeSnapshot(Long studentId, Long courseId, Double currentGrade, Long lastEventId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.currentGrade = currentGrade;
        this.lastEventId = lastEventId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Long getCourseId() {
        return courseId;
    }
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
    public Double getCurrentGrade() {
        return currentGrade;
    }
    public void setCurrentGrade(Double currentGrade) {
        this.currentGrade = currentGrade;
    }
    public Long getLastEventId() {
        return lastEventId;
    }
    public void setLastEventId(Long lastEventId) {
        this.lastEventId = lastEventId;
    }

}
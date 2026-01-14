package com.example.demo.patterns.es;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class StudentGradeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long courseId;
    private String eventType; // "CREATED", "UPDATED"
    private Double gradeValue;
    private LocalDateTime timestamp;

    public StudentGradeEvent() {}
    public StudentGradeEvent(Long studentId, Long courseId, String eventType, Double gradeValue, LocalDateTime timestamp) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.eventType = eventType;
        this.gradeValue = gradeValue;
        this.timestamp = timestamp;

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
    public String getEventType() {

        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public Double getGradeValue() {
        return gradeValue;
    }
    public void setGradeValue(Double gradeValue) {
        this.gradeValue = gradeValue;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
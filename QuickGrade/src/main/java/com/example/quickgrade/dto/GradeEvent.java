package com.example.quickgrade.dto;

public class GradeEvent {
    private String studentCode;
    private String courseCode;
    private Double value;

    public GradeEvent() {}

    public GradeEvent(String studentCode, String courseCode, Double value) {
        this.studentCode = studentCode;
        this.courseCode = courseCode;
        this.value = value;
    }

    // Getters & Setters
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
}
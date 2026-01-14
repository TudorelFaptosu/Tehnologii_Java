package com.example.gradeenricher.dto;

public class StudentEnrichedEvent {
    private String studentCode;
    private String studentName;
    private Integer studentYear;
    private String courseCode;
    private Double value;

    public StudentEnrichedEvent() {}

    public StudentEnrichedEvent(String studentCode, String studentName, Integer studentYear, String courseCode, Double value) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.studentYear = studentYear;
        this.courseCode = courseCode;
        this.value = value;
    }

    // Getters & Setters
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public Integer getStudentYear() { return studentYear; }
    public void setStudentYear(Integer studentYear) { this.studentYear = studentYear; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
}
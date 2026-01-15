package com.example.prefschedule.dto;

public class GradeEvent {
    private String studentCode;
    private String courseCode;
    private Double value;

    // Getters & Setters
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    @Override
    public String toString() {
        return "GradeEvent{student='" + studentCode + "', course='" + courseCode + "', val=" + value + "}";
    }
}
package com.example.prefschedule.dto;

public class FinalGradeEvent {
    private String studentCode;
    private String studentName;
    private Integer studentYear;

    private String courseCode;
    private String courseName;
    private Integer semester;

    private Double value;

    public FinalGradeEvent() {}

    public FinalGradeEvent(String studentCode, String studentName, Integer studentYear, String courseCode, String courseName, Integer semester, Double value) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.studentYear = studentYear;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semester = semester;
        this.value = value;
    }

    // Getters & Setters - Standard
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String s) { this.studentCode = s; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String s) { this.studentName = s; }
    public Integer getStudentYear() { return studentYear; }
    public void setStudentYear(Integer i) { this.studentYear = i; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String s) { this.courseCode = s; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String s) { this.courseName = s; }
    public Integer getSemester() { return semester; }
    public void setSemester(Integer i) { this.semester = i; }
    public Double getValue() { return value; }
    public void setValue(Double v) { this.value = v; }

    @Override
    public String toString() {
        return "FINAL[" + studentName + " got " + value + " at " + courseName + "]";
    }
}
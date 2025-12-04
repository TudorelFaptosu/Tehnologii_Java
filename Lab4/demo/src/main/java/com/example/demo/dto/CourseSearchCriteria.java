package com.example.demo.dto;

public class CourseSearchCriteria {
    private Integer year;
    private Integer semester;
    private String instructorName;

    public CourseSearchCriteria(Integer year, Integer semester, String instructorName) {
        this.year = year;
        this.semester = semester;
        this.instructorName = instructorName;
    }

    // Getters (necesari pentru a citi valorile in Repository)
    public Integer getYear() {
        return year;
    }

    public Integer getSemester() {
        return semester;
    }

    public String getInstructorName() {
        return instructorName;
    }
}
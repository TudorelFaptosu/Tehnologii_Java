package com.example.stablematch.dto;

import java.util.List;

public class MatchingProblem {
    private List<StudentCandidate> students;
    private List<CourseOption> courses;

    public List<StudentCandidate> getStudents() { return students; }
    public void setStudents(List<StudentCandidate> students) { this.students = students; }
    public List<CourseOption> getCourses() { return courses; }
    public void setCourses(List<CourseOption> courses) { this.courses = courses; }
}
package com.example.stablematch.dto;

import java.util.ArrayList;
import java.util.List;

public class StudentCandidate {
    private Long id;
    private String name;
    private List<Long> preferredCourseIds = new ArrayList<>();

    public StudentCandidate() {}

    public StudentCandidate(Long id, String name, List<Long> preferredCourseIds) {
        this.id = id;
        this.name = name;
        this.preferredCourseIds = preferredCourseIds;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Long> getPreferredCourseIds() { return preferredCourseIds; }
    public void setPreferredCourseIds(List<Long> preferredCourseIds) { this.preferredCourseIds = preferredCourseIds; }
}
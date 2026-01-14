package com.example.stablematch.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseOption {
    private Long id;
    private int capacity;
    private List<Long> preferredStudentIds = new ArrayList<>();

    public CourseOption() {}

    public CourseOption(Long id, int capacity, List<Long> preferredStudentIds) {
        this.id = id;
        this.capacity = capacity;
        this.preferredStudentIds = preferredStudentIds;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public List<Long> getPreferredStudentIds() { return preferredStudentIds; }
    public void setPreferredStudentIds(List<Long> preferredStudentIds) { this.preferredStudentIds = preferredStudentIds; }
}
package com.example.demo.dto;

import java.util.List;
import java.util.Map;

public class Solution {
    // Map CourseId -> List of StudentIds
    private Map<Long, List<Long>> assignments;

    public Solution() {}

    public Solution(Map<Long, List<Long>> assignments) {
        this.assignments = assignments;
    }

    public Map<Long, List<Long>> getAssignments() { return assignments; }
    public void setAssignments(Map<Long, List<Long>> assignments) { this.assignments = assignments; }
}
package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class PreferenceDTO {
    @NotNull
    private Long courseId;

    @NotNull
    @Min(1)
    private Integer rank;

    // Getters/Setters
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }
}
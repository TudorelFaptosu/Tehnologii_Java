package com.example.prefschedule.patterns.cqrs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "course_views")

public class CourseReadModel {
    @Id
    private String id; // Maps to Postgres ID
    private String name;
    private String description;
    private String instructorName; // Denormalized data

    public CourseReadModel() {}
    public CourseReadModel(String id, String name, String description, String instructorName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.instructorName = instructorName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getInstructorName() {
        return instructorName;
    }
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

}
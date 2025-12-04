package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructors")
public class Instructor extends Person {

    // Relatie OneToMany: Un instructor preda mai multe cursuri
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    public Instructor() {}

    public Instructor(String name, String email) {
        super(name, email);
    }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
}
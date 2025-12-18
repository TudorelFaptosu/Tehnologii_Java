package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends Person {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "study_year")
    private Integer year;

    public Student() {}

    public Student(String name, String email, String password, Role role, String code, Integer year) {
        // Apelăm constructorul din Person cu noile câmpuri
        super(name, email, password, role);
        this.code = code;
        this.year = year;
    }

    // Getters & Setters specifici
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', code='" + code + "'}";
    }
}
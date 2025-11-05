package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends Person {
    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Integer year;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}

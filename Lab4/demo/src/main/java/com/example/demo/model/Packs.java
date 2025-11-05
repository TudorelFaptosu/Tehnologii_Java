package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table( name= "packs")
public class Packs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;
    private Integer semester;
    private String name;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

package com.example.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    @Column(unique = true)
    protected String email;

    // Constructor Default
    public Person() {}

    // Constructor cu parametri
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters & Setters (Obligatoriu fara Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
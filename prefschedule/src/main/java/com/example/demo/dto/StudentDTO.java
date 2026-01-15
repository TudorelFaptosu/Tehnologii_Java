package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StudentDTO {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name must have at least 2 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    private String code;

    private Integer year;

    private String password;

    // Constructori, Getters, Setters
    public StudentDTO() {}
    public StudentDTO(String name, String email, String code, Integer year,  String password) {
        this.name = name;
        this.email = email;
        this.code = code;
        this.year = year;
        this.password = password;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
package com.example.demo.dto;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;

    // Câmpuri specifice studentului
    private String code;
    private Integer year;

    // Constructor gol
    public RegisterRequest() {
    }

    // Constructor complet
    public RegisterRequest(String name, String email, String password, String code, Integer year) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.year = year;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCode() {
        return code;
    }

    public Integer getYear() {
        return year;
    }

    // --- Setters ---
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
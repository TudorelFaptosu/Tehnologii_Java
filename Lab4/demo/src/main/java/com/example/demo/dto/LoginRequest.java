package com.example.demo.dto;

public class LoginRequest {
    private String email;
    private String password;

    // Constructor gol (obligatoriu pentru deserializare JSON)
    public LoginRequest() {
    }

    // Constructor cu parametri (opțional, util pentru teste)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // --- Getters și Setters ---

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
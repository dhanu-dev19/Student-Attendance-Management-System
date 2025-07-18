package com.attendance.model;

import java.sql.Timestamp;

public class Teacher {
    private int id;
    private String name;
    private String email;
    private String password;
    private String subject;
    private String phone;
    private Timestamp createdAt;

    // Constructors
    public Teacher() {}

    public Teacher(int id, String name, String email, String password, String subject, String phone, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.subject = subject;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}


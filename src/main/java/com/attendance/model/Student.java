package com.attendance.model;

import java.sql.Timestamp;

public class Student {
    private int id;
    private String name;
    private String email;
    private String className;
    private String password;
    private String rollNumber;
    private String branch;
    private int year;
    private Timestamp createdAt;

    // Constructors
    public Student() {}

    public Student(int id, String name, String email, String password, String className, String rollNumber,String branch,int year) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.className = className;
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.year = year;
        // createdAt will be set by the database or handled separately if needed for new student
    }

    public Student(int id, String name, String email, String className, String password, String rollNumber, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.className = className;
        this.password = password;
        this.rollNumber = rollNumber;
        this.createdAt = createdAt;
    }

    public Student(int id, String name, String email, String password, String className, String rollNumber,String branch,int year, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.className = className;
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.year = year;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}


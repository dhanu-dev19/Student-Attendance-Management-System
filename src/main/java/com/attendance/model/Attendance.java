package com.attendance.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Attendance
{
    private int id;
    private int studentId;
    private int subjectId;
    private Date date;
    private String status;
    private Timestamp createdAt;
    private String studentName;
    private String subjectName;
    private double attendancePercentage;



    public Attendance(){}

    public Attendance(int id, int studentId,int subjectId,Date date,String status , Timestamp createdAt)
    {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.date = date;
        this.status = status;
        this.createdAt = createdAt;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }
}


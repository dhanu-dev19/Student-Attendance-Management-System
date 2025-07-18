<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Details - Attendance Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

    <style>
        div a{
        text-decoration:none;
        }
        </style>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <h1>Teacher Details</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/teachers?action=list">Back to Manage Teachers</a>
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </nav>
    </header>

    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>${teacherDetails.name}</h2>
            </div>
            <div class="card-body">
                <p><strong>Email:</strong> ${teacherDetails.email}</p>
                <p><strong>Subject:</strong> ${teacherDetails.subject}</p>
                <p><strong>Phone:</strong> ${teacherDetails.phone}</p>
                <p><strong>Account Created:</strong> ${teacherDetails.createdAt}</p>
            </div>
            <div class="card-footer">
                <a href="${pageContext.request.contextPath}/admin/teachers?action=edit&id=${teacherDetails.id}" class="btn btn-primary">Edit Teacher</a>
                <a href="${pageContext.request.contextPath}/admin/teachers?action=delete&id=${teacherDetails.id}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this teacher?')">Delete Teacher</a>
            </div>
        </div>
    </div>
</body>
</html>
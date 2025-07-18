<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Details - Attendance Management System</title>
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
            <h1>Student Details</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/admin/student">Manage Students</a>
                <a href="${pageContext.request.contextPath}/admin/teachers">Manage Teachers</a>
                <a href="${pageContext.request.contextPath}/admin/subjects">Manage Subjects</a>
                <a href="${pageContext.request.contextPath}/admin/attendance/view">View Attendance</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </nav>
    </header>

    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>Details for Student: <c:out value="${studentDetails.name}"/></h2>
            </div>
            <div class="card-body">
                <p><strong>ID:</strong> <c:out value="${studentDetails.id}"/></p>
                <p><strong>Name:</strong> <c:out value="${studentDetails.name}"/></p>
                <p><strong>Email:</strong> <c:out value="${studentDetails.email}"/></p>
                <p><strong>Class:</strong> <c:out value="${studentDetails.className}"/></p>
                <p><strong>Roll Number:</strong> <c:out value="${studentDetails.rollNumber}"/></p>
                <p><strong>Created At:</strong> <c:out value="${studentDetails.createdAt}"/></p>
                <a href="${pageContext.request.contextPath}/admin/student?action=edit&id=${studentDetails.id}" class="btn btn-primary">Edit Student</a>
                <a href="${pageContext.request.contextPath}/admin/student" class="btn btn-primary">Back to Manage Students</a>
            </div>
        </div>
    </div>
</body>
</html>
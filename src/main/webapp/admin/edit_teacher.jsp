<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Teacher - Attendance Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <header class="header">
        <nav class="nav">
            <h1>Edit Teacher</h1>
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
                <h2>Edit Teacher Details</h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/teachers" method="post" class="form">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${teacher.id}">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" class="form-control" id="name" name="name" value="${teacher.name}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="${teacher.email}" required>
                </div>
                <div class="form-group">
                    <label for="password">Password (leave blank to keep current)</label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>
                <div class="form-group">
                    <label for="subject">Subject</label>
                    <input type="text" class="form-control" id="subject" name="subject" value="${teacher.subject}" required>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" class="form-control" id="phone" name="phone" value="${teacher.phone}" required>
                </div>
                <button type="submit" class="btn btn-primary">Update Teacher</button>
            </form>
        </div>
    </div>
</body>
</html>
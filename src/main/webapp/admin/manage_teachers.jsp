<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Teachers - Attendance Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        td a{
        text-decoration:none;
        }
        </style>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <h1>Manage Teachers</h1>
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
                <h2>Add New Teacher</h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/teachers" method="post" class="form">
                <input type="hidden" name="action" value="insert">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" class="form-control" id="name" name="name" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="subject">Subject</label>
                    <input type="text" class="form-control" id="subject" name="subject" required>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" class="form-control" id="phone" name="phone" required>
                </div>
                <button type="submit" class="btn btn-primary">Add Teacher</button>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Search Teachers</h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/teachers" method="get" class="form">
             <input type="hidden" name="action" value="search"/>
                <div class="form-group">
                    <input type="text" class="form-control" name="query" placeholder="Search by name, email, or subject">
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Teacher List</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Subject</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${teachers}" var="teacher">
                        <tr>
                            <td>${teacher.name}</td>
                            <td>${teacher.email}</td>
                            <td>${teacher.subject}</td>
                            <td>${teacher.phone}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/teachers?action=edit&id=${teacher.id}"
                                   class="btn btn-primary">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/teachers?action=delete&id=${teacher.id}"
                                   class="btn btn-danger"
                                   onclick="return confirm('Are you sure you want to delete this teacher?')">Delete</a>
                                <a href="${pageContext.request.contextPath}/admin/teachers?action=view&id=${teacher.id}"
                                   class="btn btn-primary">View Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${not empty message}">
            <div class="alert ${messageType}">
                ${message}
            </div>
        </c:if>
    </div>
</body>
</html>
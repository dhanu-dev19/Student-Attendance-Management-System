<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Subjects - Attendance Management System</title>
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
            <h1>Manage Subjects</h1>
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
                <h2><c:out value="${(subject != null) ? 'Edit Subject' : 'Add New Subject'}"/></h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/subjects" method="post" class="form">
                <c:if test="${subject != null}">
                    <input type="hidden" name="id" value="<c:out value='${subject.id}'/>">
                </c:if>
                <div class="form-group">
                    <label for="name">Subject Name</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="<c:out value="${(subject != null) ? subject.name : ''}"/>" required>
                </div>
                <div class="form-group">
                    <label for="code">Subject Code</label>
                    <input type="text" class="form-control" id="code" name="code"
                           value="<c:out value="${(subject != null) ? subject.code : ''}"/>" required>
                </div>
                <div class="form-group">
                    <label for="teacherId">Teacher</label>
                    <select class="form-control" id="teacherId" name="teacherId" required>
                        <option value="">Select Teacher</option>
                        <c:forEach items="${teachers}" var="teacher">
                            <option value="${teacher.id}"
                                <c:if test="${subject != null && subject.teacherId == teacher.id}">selected</c:if>>
                                <c:out value="${teacher.name} (${teacher.subject})"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <input type="hidden" name="action" value="<c:out value="${(subject != null) ? 'update' : 'add'}"/>">
                <button type="submit" class="btn btn-primary">
                    <c:out value="${(subject != null) ? 'Update Subject' : 'Add Subject'}"/>
                </button>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Search Subjects</h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/subjects" method="get" class="form">
                <div class="form-group">
                    <input type="text" class="form-control" name="query" placeholder="Search by subject name or teacher">
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Subject List</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>Subject Name</th>
                        <th>Subject Code</th>
                        <th>Teacher ID</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listSubjects}" var="subject">
                        <tr>
                            <td>${subject.name}</td>
                            <td>${subject.code}</td>
                            <td>${subject.teacherId}</td>

                            <td>
                                <a href="${pageContext.request.contextPath}/admin/subjects?action=edit&id=${subject.id}"
                                   class="btn btn-primary">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/subjects?action=delete&id=${subject.id}"
                                   class="btn btn-danger"
                                   onclick="return confirm('Are you sure you want to delete this subject?')">Delete</a>
                                <a href="${pageContext.request.contextPath}/admin/subjects?action=view&id=${subject.id}"
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Students - Attendance Management System</title>
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
            <h1>Manage Students</h1>
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
                <h2><c:out value="${student != null ? 'Edit Student' : 'Add New Student'}"/></h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/student" method="post" class="form">
                <c:if test="${student != null}">
                    <input type="hidden" name="action" value="update"/>
                    <input type="hidden" name="id" value="${student.id}"/>
                </c:if>
                <c:if test="${student == null}">
                    <input type="hidden" name="action" value="insert"/>
                </c:if>
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" class="form-control" id="name" name="name" value="<c:out value="${student.name}"/>" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="<c:out value="${student.email}"/>" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" value="" placeholder="Leave blank to keep current password" <c:if test="${student == null}">required</c:if>>
                </div>
                <div class="form-group">
                    <label for="className">Class</label>
                    <input type="text" class="form-control" id="className" name="className" value="<c:out value="${student.className}"/>" required>
                </div>
                <div class="form-group">
                    <label for="rollNumber">Roll Number</label>
                    <input type="text" class="form-control" id="rollNumber" name="rollNumber" value="<c:out value="${student.rollNumber}"/>" required>
                </div>
                 <div class="form-group">
                                     <label for="branch">Branch</label>
                                     <select name="branch" id="branch" class="form-control" required>
                                         <option value="">Select a branch</option>
                                         <option value="CSE">CSE</option>
                                         <option value="ECE">ECE</option>
                                         <option value="ISE">ISE</option>
                                         <option value="AI/ML">AI/ML</option>
                                         <option value="ME">ME</option>
                                         <option value="CIVIL">CIVIL</option>
                                     </select>
                                 </div>
                                 <div class="form-group">
                                     <label for="year">Year</label>
                                     <select name="year" id="year" class="form-control" required>
                                         <option value="">Select a year</option>
                                         <option value="2022">2022</option>
                                         <option value="2023">2023</option>
                                         <option value="2024">2024</option>
                                         <option value="2025">2025</option>
                                     </select>
                                 </div>
                <button type="submit" class="btn btn-primary"><c:out value="${student != null ? 'Update Student' : 'Add Student'}"/></button>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Search Students</h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/student" method="get" class="form">
                <input type="hidden" name="action" value="search"/>
                <div class="form-group">
                    <input type="text" class="form-control" name="query" placeholder="Search by name, email, or roll number">
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Student List</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>Roll Number</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Class</th>
                        <th>Branch</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${students}" var="student">
                        <tr>
                            <td>${student.rollNumber}</td>
                            <td>${student.name}</td>
                            <td>${student.email}</td>
                            <td>${student.className}</td>
                            <td>${student.branch}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/student?action=edit&id=${student.id}"
                                   class="btn btn-primary">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/student?action=delete&id=${student.id}"
                                   class="btn btn-danger"
                                   onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
                                <a href="${pageContext.request.contextPath}/admin/student?action=view&id=${student.id}"
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
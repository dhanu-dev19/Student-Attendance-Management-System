<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Attendance - Attendance Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/chart.min.js"></script>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <h1>View Attendance</h1>
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
                <h2>Filter Attendance</h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/attendance/view" method="get" class="form">
                <div class="form-group">
                    <label for="subjectId">Subject</label>
                    <select class="form-control" id="subjectId" name="subjectId">
                        <option value="">All Subjects</option>
                        <c:forEach items="${subjects}" var="subject">
                            <option value="${subject.id}" ${param.subjectId == subject.id ? 'selected' : ''}>
                                ${subject.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="className">Class</label>
                    <select class="form-control" id="className" name="className">
                        <option value="">All Classes</option>
                        <c:forEach items="${classes}" var="className">
                            <option value="${className}" ${param.className == className ? 'selected' : ''}>
                                ${className}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="branch">Branch</label>
                    <select class="form-control" id="branch" name="branch">
                        <option value="">All Branches</option>
                        <c:forEach items="${branches}" var="branchName">
                            <option value="${branchName}" ${param.branch == branchName ? 'selected' : ''}>
                                ${branchName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="year">Year</label>
                    <select class="form-control" id="year" name="year">
                        <option value="">All Years</option>
                        <c:forEach items="${years}" var="yearVal">
                            <option value="${yearVal}" ${param.year == yearVal ? 'selected' : ''}>
                                ${yearVal}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="date">Date</label>
                    <input type="date" class="form-control" id="date" name="date" value="${param.date}">
                </div>
                <button type="submit" class="btn btn-primary">Apply Filters</button>
            </form>
        </div>



        <div class="card">
            <div class="card-header">
                <h2>Attendance Records</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Subject</th>
                        <th>Student</th>
                        <th>Class</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${attendanceRecords}" var="record">
                        <tr>
                            <td>${record.date}</td>
                            <td>${record.subjectName}</td>
                            <td>${record.studentName}</td>
                            <td>${record.className}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${record.status == 'PRESENT'}">
                                        <span class="status-present">Present</span>
                                    </c:when>
                                    <c:when test="${record.status == 'ABSENT'}">
                                        <span class="status-absent">Absent</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-late">Late</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>

                                <a href="${pageContext.request.contextPath}/admin/attendance/delete?id=${record.id}"
                                   class="btn btn-danger"
                                   onclick="return confirm('Are you sure you want to delete this attendance record?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Low Attendance Alerts</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>Student</th>
                        <th>Subject</th>
                        <th>Attendance %</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${lowAttendanceAlerts}" var="alert">
                        <tr>
                            <td>${alert.studentName}</td>
                            <td>${alert.subjectName}</td>
                            <td class="status-absent">${alert.attendancePercentage}%</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/attendance/view?studentId=${alert.studentId}&subjectId=${alert.subjectId}"
                                   class="btn btn-primary">View Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


</body>
</html>
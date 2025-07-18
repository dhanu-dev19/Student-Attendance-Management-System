<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Attendance Details - Attendance Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <header class="header">
        <nav class="nav">
            <h1>Attendance Details</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/admin/attendance/view">Back to Attendance Overview</a>
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </nav>
    </header>
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>Student: <c:out value="${student.name}"/> (Roll: <c:out value="${student.rollNumber}"/>)</h2>
                <h3>Subject: <c:out value="${subject.name}"/> (<c:out value="${subject.code}"/>)</h3>
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${attendanceRecords}" var="record">
                            <tr>
                                <td><c:out value="${record.date}"/></td>
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
                            </tr>
                        </c:forEach>
                        <c:if test="${empty attendanceRecords}">
                            <tr>
                                <td colspan="2">No attendance records found for this student in this subject.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
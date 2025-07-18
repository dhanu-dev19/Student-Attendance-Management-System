<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance Charts - Attendance Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
      <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <h1>Attendance Charts</h1>
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
                <h2>Filter Charts</h2>
            </div>
            <form action="${pageContext.request.contextPath}/admin/attendance/charts" method="get" class="form">
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
                    <label for="class">Class</label>
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
                    <label for="startDate">Start Date</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${param.startDate}">
                </div>
                <div class="form-group">
                    <label for="endDate">End Date</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" value="${param.endDate}">
                </div>
                <button type="submit" class="btn btn-primary">Apply Filters</button>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Overall Attendance Trend</h2>
            </div>
            <canvas id="overallTrendChart" style="width: 100%; height: 300px;"></canvas>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Subject-wise Attendance</h2>
            </div>
            <canvas id="subjectWiseChart" style="width: 100%; height: 300px;"></canvas>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Class-wise Attendance</h2>
            </div>
            <canvas id="classWiseChart" style="width: 100%; height: 300px;"></canvas>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Attendance Distribution</h2>
            </div>
            <canvas id="attendanceDistributionChart" style="width: 100%; height: 300px;"></canvas>
        </div>
    </div>

    <script>
        // Initialize overall trend chart
        const overallTrendCtx = document.getElementById('overallTrendChart').getContext('2d');
        const overallTrendData = {
            labels: JSON.parse('${overallTrendDates}'),
            datasets: [{
                label: 'Overall Attendance %',
                data: JSON.parse('${overallTrendPercentages}'),
                borderColor: '#6f42c1',
                tension: 0.1
            }]
        };

        new Chart(overallTrendCtx, {
            type: 'line',
            data: overallTrendData,
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });

        // Initialize subject-wise chart
        const subjectWiseCtx = document.getElementById('subjectWiseChart').getContext('2d');
        const subjectWiseData = {
            labels: JSON.parse('${subjectNames}'),
            datasets: [{
                label: 'Average Attendance %',
                data: JSON.parse('${subjectAttendancePercentages}'),
                backgroundColor: '#fd7e14'
            }]
        };

        new Chart(subjectWiseCtx, {
            type: 'bar',
            data: subjectWiseData,
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });

        // Initialize class-wise chart
        const classWiseCtx = document.getElementById('classWiseChart').getContext('2d');
        const classWiseData = {
            labels: JSON.parse('${classNames}'),
            datasets: [{
                label: 'Average Attendance %',
                data: JSON.parse('${classAttendancePercentages}'),
                backgroundColor: '#4e73df'
            }]
        };

        new Chart(classWiseCtx, {
            type: 'bar',
            data: classWiseData,
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });

        // Initialize attendance distribution chart
        const distributionCtx = document.getElementById('attendanceDistributionChart').getContext('2d');
        const distributionData = {
            labels: ['Present', 'Absent', 'Late'],
            datasets: [{
                data: JSON.parse('${attendanceDistribution}'),
                backgroundColor: ['#4CAF50', '#f44336', '#ff9800']
            }]
        };

        new Chart(distributionCtx, {
            type: 'pie',
            data: distributionData,
            options: {
                responsive: true
            }
        });
    </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Attendance Management System</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            background: #f4f6fb;
            font-family: 'Roboto', sans-serif;
            margin: 0;
        }
        .header {
            background: #4CAF50;
            color: #fff;
            padding: 1.5rem 0;
            box-shadow: 0 2px 8px rgba(0,0,0,0.04);
        }
        .nav {
            display: flex;
            align-items: center;
            justify-content: space-between;
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
        }
        .nav h1 {
            font-size: 18px;
            font-weight: 700;
            letter-spacing: 1px;
        }
        .nav-links a {
            color: #fff;
            text-decoration: none;
            margin-left: 32px;
            font-size:16px;
            font-weight: 500;
            transition: color 0.2s;
        }
        .nav-links a:hover {
            color: #ffd93d;
        }
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        .stats-container {
            display: flex;
            gap: 2rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
        }
        .stat-card {
            flex: 1 1 200px;
            background: #fff;
            border-radius: 18px;
            box-shadow: 0 2px 12px rgba(76,175,80,0.08);
            padding: 2rem 1.5rem;
            display: flex;
            flex-direction: column;
            align-items: center;
            transition: box-shadow 0.2s;
            position: relative;
        }
        .stat-card:hover {
            box-shadow: 0 4px 24px rgba(76,175,80,0.16);
        }
        .stat-card i {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
            color: #4CAF50;
        }
        .stat-card h3 {
            margin: 0.5rem 0 0.2rem 0;
            font-size: 1.2rem;
            font-weight: 700;
        }
        .stat-number {
            font-size: 2.2rem;
            font-weight: 700;
            color: #222;
        }
        .card {
            background: #fff;
            border-radius: 18px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.06);
            margin-bottom: 2rem;
            padding: 2rem;
        }
        .card-header h2 {
            margin: 0 0 1rem 0;
            font-size: 1.3rem;
            font-weight: 700;
            color: #4CAF50;
        }
        .quick-actions {
            display: flex;
            gap: 1.5rem;
            flex-wrap: wrap;
        }
        .btn {
            background: #4CAF50;
            color: #fff;
            border: none;
            border-radius: 50px;
            padding: 0.8rem 2rem;
            font-size: 1rem;
            font-weight: 500;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            transition: background 0.2s, box-shadow 0.2s;
            box-shadow: 0 2px 8px rgba(76,175,80,0.08);
        }
        .btn:hover {
            background: #388e3c;
            box-shadow: 0 4px 16px rgba(76,175,80,0.16);
        }
        .table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        .table th, .table td {
            padding: 0.9rem 0.7rem;
            text-align: left;
        }
        .table th {
            background: #f4f6fb;
            color: #333;
            font-weight: 600;
        }
        .table tr:nth-child(even) {
            background: #f9fafc;
        }
        .status-absent {
            color: #e53935;
            font-weight: 700;
        }
        @media (max-width: 900px) {
            .stats-container {
                flex-direction: column;
                gap: 1rem;
            }
            .quick-actions {
                flex-direction: column;
                gap: 1rem;
            }
        }
    </style>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <h1>Admin Dashboard</h1>
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
        <div class="stats-container">
            <div class="stat-card">
                <i class="fas fa-user-graduate"></i>
                <h3>Total Students</h3>
                <p class="stat-number">${totalStudents}</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-chalkboard-teacher"></i>
                <h3>Total Teachers</h3>
                <p class="stat-number">${totalTeachers}</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-book"></i>
                <h3>Total Subjects</h3>
                <p class="stat-number">${totalSubjects}</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-chart-line"></i>
                <h3>Average Attendance</h3>
                <p class="stat-number">${averageAttendance}%</p>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Quick Actions</h2>
            </div>
            <div class="quick-actions">
                <a href="${pageContext.request.contextPath}/admin/manage_students.jsp" class="btn">
                    <i class="fas fa-user-plus"></i> Add New Student
                </a>
                <a href="${pageContext.request.contextPath}/admin/manage_teachers.jsp" class="btn">
                    <i class="fas fa-user-tie"></i> Add New Teacher
                </a>
                <a href="${pageContext.request.contextPath}/admin/manage_subjects.jsp" class="btn">
                    <i class="fas fa-book-medical"></i> Add New Subject
                </a>
                <a href="${pageContext.request.contextPath}/admin/attendance/charts" class="btn">
                    <i class="fas fa-chart-bar"></i> View Attendance Reports
                </a>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Attendance Overview</h2>
            </div>
            <canvas id="attendanceChart" style="width: 100%; height: 300px;"></canvas>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Recent Activities</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Teacher</th>
                        <th>Subject</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${recentActivities}" var="activity">
                        <tr>
                            <td>${activity.date}</td>
                            <td>${activity.teacherName}</td>
                            <td>${activity.subjectName}</td>
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
                                        <form action="${pageContext.request.contextPath}/admin/send-sms" method="post" style="display:inline;">
                                            <input type="hidden" name="teacherPhone" value="${alert.teacherPhone}" />
                                            <input type="hidden" name="studentName" value="${alert.studentName}" />
                                            <input type="hidden" name="subjectName" value="${alert.subjectName}" />
                                            <button type="submit" class="btn btn-primary" onclick="return confirm('Send SMS to teacher?');">
                                                <i class="fas fa-sms"></i> Send SMS
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
    </div>

    <script>
        const ctx = document.getElementById('attendanceChart').getContext('2d');

        // Create a gradient for the line
        const gradient = ctx.createLinearGradient(0, 0, 0, 300);
        gradient.addColorStop(0, '#ffd93d');
        gradient.addColorStop(1, '#ff6b6b');

        const attendanceData = {
            labels: ${attendanceDatesJson},
            datasets: [{
                label: 'Attendance Percentage',
                data: ${attendancePercentagesJson},
                borderColor: gradient,
                backgroundColor: 'rgba(255, 215, 61, 0.15)', // semi-transparent fill
                pointBackgroundColor: '#ff6b6b',
                pointBorderColor: '#ffd93d',
                pointRadius: 5,
                fill: true,
                tension: 0.3
            }]
        };

        const chartConfig = {
            type: 'line',
            data: attendanceData,
            options: {
                responsive: true,
                plugins: {
                    legend: { labels: { color: '#fff' } }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100,
                        ticks: { color: '#fff' }
                    },
                    x: {
                        ticks: { color: '#fff' }
                    }
                }
            }
        };

        new Chart(ctx, chartConfig);
    </script>
</body>
</html>
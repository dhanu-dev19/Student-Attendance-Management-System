<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Attendance - Attendance Management System</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            background: linear-gradient(135deg, #0f0c29 0%, #302b63 50%, #24243e 100%);
            font-family: 'Poppins', sans-serif;
            color: #fff;
            margin: 0;
            min-height: 100vh;
        }
        .header {
            background: rgba(255,255,255,0.08);
            backdrop-filter: blur(8px);
            padding: 2rem 0 1rem 0;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.10);
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
            font-size: 2.5rem;
            font-weight: 700;
            letter-spacing: 1px;
            background: linear-gradient(45deg, #6bafff, #ffd93d);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        .nav-links a {
            color: #fff;
            text-decoration: none;
            margin-left: 2rem;
            font-weight: 500;
            font-size: 1.1rem;
            transition: color 0.2s;
            position: relative;
        }
        .nav-links a:after {
            content: '';
            display: block;
            width: 0;
            height: 2px;
            background: #ffd93d;
            transition: width .3s;
            position: absolute;
            bottom: -4px;
            left: 0;
        }
        .nav-links a:hover:after, .nav-links a.active:after {
            width: 100%;
        }
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        .card {
            background: rgba(255,255,255,0.10);
            border-radius: 24px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.10);
            margin-bottom: 2rem;
            padding: 2rem;
            backdrop-filter: blur(8px);
        }
        .card-header h2 {
            margin: 0 0 1rem 0;
            font-size: 1.3rem;
            font-weight: 600;
            color: #ffd93d;
        }
        .form {
            display: flex;
            gap: 3rem;
            flex-wrap: wrap;
            align-items: flex-end;
            margin-bottom: 2rem;
        }
        .form-group {
            flex: 1 1 200px;
            display: flex;
            flex-direction: column;
        }
        .form-group label {
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: #ffd93d;
        }
        .form-control {
            padding: 0.8rem;
            border-radius: 12px;
            border: 1px solid rgba(255,255,255,0.15);
            background: rgba(255,255,255,0.08);
            color: #fff;
            font-size: 1rem;
        }
        .btn {
            background: linear-gradient(45deg, #6bafff, #ffd93d);
            color: #fff;
            border: none;
            border-radius: 50px;
            padding: 0.7rem 1.5rem;
            font-size: 0.9rem;
            font-weight: 600;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            box-shadow: 0 4px 15px rgba(107, 175, 255, 0.15);
            transition: background 0.2s, box-shadow 0.2s, transform 0.2s;
            margin-right: 0.5rem;
            margin-bottom: 0.5rem;
        }
        .btn:hover {
            background: linear-gradient(45deg, #ffd93d, #6bafff);
            color: #222;
            transform: translateY(-2px) scale(1.02);
            box-shadow: 0 8px 24px rgba(107, 175, 255, 0.25);
        }
        .table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
            background: rgba(255,255,255,0.08);
            border-radius: 16px;
            overflow: hidden;
        }
        .table th, .table td {
            padding: 1rem 0.7rem;
            text-align: left;
            color: #fff;
            border-bottom: 1px solid rgba(255,255,255,0.05);
        }
        .table th {
            background: rgba(255,255,255,0.13);
            color: #ffd93d;
            font-weight: 600;
        }
        .table tr:hover {
            background: rgba(255,255,255,0.15);
        }
        .table tbody tr:last-child td {
            border-bottom: none;
        }
        .status-absent {
            color: #ff6b6b;
            font-weight: 700;
        }
        .status-present {
            color: #4CAF50;
            font-weight: 700;
        }
        .no-data {
            text-align: center;
            padding: 2rem;
            color: rgba(255,255,255,0.7);
            font-style: italic;
        }
        @media (max-width: 900px) {
            .nav {
                flex-direction: column;
                text-align: center;
            }
            .nav-links {
                margin-top: 1rem;
                flex-wrap: wrap;
                justify-content: center;
            }
            .nav-links a {
                margin: 0.5rem;
            }
            .form {
                flex-direction: column;
                gap: 1rem;
            }
            .table {
                overflow-x: auto;
                display: block;
                white-space: nowrap;
            }
            .table thead, .table tbody, .table th, .table td, .table tr {
                display: block;
            }
            .table th, .table td {
                width: auto !important;
                text-align: right !important;
            }
            .table th:before {
                content: attr(data-label);
                float: left;
                font-weight: bold;
                text-transform: uppercase;
            }
            .table tbody tr {
                margin-bottom: 1em;
                border: 1px solid rgba(255,255,255,0.1);
                border-radius: 8px;
                padding: 1em;
            }
            .table td:last-child {
                border-bottom: 0;
            }
        }
    </style>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <h1>View Attendance</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/student/dashboard"><i class="fas fa-home"></i> Dashboard</a>
                <a href="${pageContext.request.contextPath}/student/view-attendance" class="active"><i class="fas fa-calendar-check"></i> View Attendance</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
            </div>
        </nav>
    </header>

    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2><i class="fas fa-filter"></i> Filter Attendance</h2>
            </div>
            <form class="form" method="get">
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
                    <label for="startDate">Start Date</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${param.startDate}">
                </div>
                <div class="form-group">
                    <label for="endDate">End Date</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" value="${param.endDate}">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn"><i class="fas fa-search"></i> Apply Filters</button>
                </div>
            </form>
        </div>

        <div class="card">
            <div class="card-header">
                <h2><i class="fas fa-chart-line"></i> Attendance Overview</h2>
            </div>
            <c:choose>
                <c:when test="${not empty attendanceDates && not empty attendancePercentages}">
                    <canvas id="attendanceChart" style="width: 100%; height: 300px;"></canvas>
                </c:when>
                <c:otherwise>
                    <p class="no-data">No attendance data available for chart.</p>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="card">
            <div class="card-header">
                <h2><i class="fas fa-list"></i> Attendance Records</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th data-label="Date">Date</th>
                        <th data-label="Subject">Subject</th>
                        <th data-label="Status">Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty attendanceRecords}">
                            <c:forEach items="${attendanceRecords}" var="record">
                                <tr>
                                    <td data-label="Date">${record.date}</td>
                                    <td data-label="Subject">${record.subjectName}</td>
                                    <td data-label="Status">
                                        <c:choose>
                                            <c:when test="${record.status == 'PRESENT'}">
                                                <span class="status-present">Present</span>
                                            </c:when>
                                            <c:when test="${record.status == 'ABSENT'}">
                                                <span class="status-absent">Absent</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="3" class="no-data">No attendance records found for the selected filters.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <div class="card">
            <div class="card-header">
                <h2><i class="fas fa-table"></i> Subject-wise Summary</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th data-label="Subject">Subject</th>
                        <th data-label="Total Classes">Total Classes</th>
                        <th data-label="Present">Present</th>
                        <th data-label="Absent">Absent</th>
                        <th data-label="Attendance %">Attendance %</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty subjectSummary}">
                            <c:forEach items="${subjectSummary}" var="summary">
                                <tr>
                                    <td data-label="Subject">${summary.subjectName}</td>
                                    <td data-label="Total Classes">${summary.totalClasses}</td>
                                    <td data-label="Present">${summary.presentCount}</td>
                                    <td data-label="Absent">${summary.absentCount}</td>
                                    <td data-label="Attendance %">
                                        <c:choose>
                                            <c:when test="${summary.attendancePercentage >= 75}">
                                                <span class="status-present">${summary.attendancePercentage}%</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-absent">${summary.attendancePercentage}%</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="no-data">No summary data available.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        // Initialize attendance chart
        const ctx = document.getElementById('attendanceChart');
        if (ctx) {
            const chart = new Chart(ctx.getContext('2d'), {
                type: 'line',
                data: {
                    labels: JSON.parse('${attendanceDates}'),
                    datasets: [{
                        label: 'Attendance Percentage',
                        data: JSON.parse('${attendancePercentages}'),
                        borderColor: '#1e88e5',
                        backgroundColor: 'rgba(30, 136, 229, 0.2)',
                        borderWidth: 3,
                        pointBackgroundColor: '#1e88e5',
                        pointBorderColor: '#ffffff',
                        pointBorderWidth: 2,
                        pointRadius: 6,
                        pointHoverRadius: 8,
                        fill: true,
                        tension: 0.1
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            labels: {
                                font: {
                                    size: 14,
                                    weight: 'bold'
                                },
                                color: '#263238'
                            }
                        },
                        tooltip: {
                            backgroundColor: 'rgba(38, 50, 56, 0.9)',
                            titleFont: { size: 14 },
                            bodyFont: { size: 14 },
                            padding: 12,
                            displayColors: false
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            max: 100,
                            grid: {
                                color: 'rgba(0,0,0,0.08)'
                            },
                            ticks: {
                                color: '#fff',
                                font: {
                                    size: 13,
                                    weight: 'bold'
                                },
                                callback: function(value) {
                                    return value + '%';
                                }
                            },
                            title: {
                                display: true,
                                text: 'Attendance Percentage',
                                color: '#fff',
                                font: {
                                    size: 14,
                                    weight: 'bold'
                                }
                            }
                        },
                        x: {
                            grid: {
                                display: false
                            },
                            ticks: {
                                color: '#fff',
                                font: {
                                    size: 12,
                                    weight: 'bold'
                                }
                            },
                            title: {
                                display: true,
                                text: 'Date',
                                color: '#fff',
                                font: {
                                    size: 14,
                                    weight: 'bold'
                                }
                            }
                        }
                    },
                    elements: {
                        line: {
                            borderWidth: 3
                        }
                    }
                }
            });
        }
    </script>
</body>
</html>
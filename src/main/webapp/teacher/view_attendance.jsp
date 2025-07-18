<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Attendance - Teacher</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            background: linear-gradient(135deg, #0f0c29 0%, #302b63 50%, #24243e 100%);
            font-family: 'Poppins', sans-serif;
            color: #ffffff;
            min-height: 100vh;
            margin: 0;
            padding: 2rem;
        }

        .attendance-container {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 2rem;
            box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
            border: 1px solid rgba(255, 255, 255, 0.1);
            margin-bottom: 2rem;
        }

        h1 {
            font-size: 2.5rem;
            font-weight: 700;
            margin-bottom: 2rem;
            background: linear-gradient(45deg, #ff6b6b, #ffd93d);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            text-shadow: 0 2px 10px rgba(255, 107, 107, 0.3);
        }

        .filter-form {
            display: flex;
            gap: 3rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;

        }

        .form-group {
            flex: 1;
            min-width: 200px;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #ffffff;
            font-weight: 500;
        }

        .form-control {
            width: 100%;
            padding: 0.8rem;
            border: 2px solid rgba(255, 255, 255, 0.1);
            border-radius: 12px;
            background: rgba(255, 255, 255, 0.05);
            color: #ffffff;
            font-size: 1rem;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            outline: none;
            border-color: #ff6b6b;
            box-shadow: 0 0 0 3px rgba(255, 107, 107, 0.2);
        }

        .btn {
            padding: 0.8rem 1.5rem;
            border-radius: 50px;
            font-size: 1rem;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
        }

        .btn-primary {
            background: linear-gradient(45deg, #ff6b6b, #ffd93d);
            color: #ffffff;
            box-shadow: 0 4px 15px rgba(255, 107, 107, 0.3);
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(255, 107, 107, 0.4);
        }

        .btn-secondary {
            background: rgba(255, 255, 255, 0.1);
            color: #ffffff;
            border: 1px solid rgba(255, 255, 255, 0.2);
         }

         .btn-secondary:hover {
            background: rgba(255, 255, 255, 0.15);
            transform: translateY(-2px);
         }

        .attendance-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            margin-top: 1rem;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 12px;
            overflow: hidden;
        }

        .attendance-table th,
        .attendance-table td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }

        .attendance-table th {
            background: rgba(255, 255, 255, 0.1);
            font-weight: 500;
            color: #ffffff;
        }

        .attendance-table tr:hover {
            background: rgba(255, 255, 255, 0.1);
        }

        .status-present {
            color: #4CAF50;
        }

        .status-absent {
            color: #f44336;
        }

        .chart-container {
            margin-top: 2rem;
            padding: 1.5rem;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 12px;
            border: 1px solid rgba(255, 255, 255, 0.1);
        }

        .alert {
            padding: 1rem;
            border-radius: 12px;
            margin-bottom: 1.5rem;
            font-size: 1rem;
        }

        .alert-danger {
            background: rgba(255, 107, 107, 0.1);
            border: 1px solid rgba(255, 107, 107, 0.3);
            color: #ff6b6b;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="attendance-container">
            <h1>View Attendance Records</h1>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <i class="fas fa-exclamation-circle"></i>
                    ${error}
                </div>
            </c:if>

            <form class="filter-form" method="get">
                <div class="form-group">
                    <label for="subject">Subject</label>
                    <select class="form-control" id="subject" name="subjectId">
                        <c:forEach items="${subjects}" var="subject">
                            <option value="${subject.id}" ${param.subjectId == subject.id ? 'selected' : ''}>
                                ${subject.name}
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
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-search"></i>
                    Filter
                </button>
            </form>

            <div class="chart-container">
                <canvas id="attendanceChart"></canvas>
            </div>

            <table class="attendance-table">
                <thead>
                    <tr>
                        <th>Student ID</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Subject</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${attendanceRecords}" var="record">
                        <tr>
                            <td>${record.studentId}</td>
                            <td>${record.studentName}</td>
                            <td>
                                <span class="status-${record.status.toLowerCase()}">
                                    <i class="fas ${record.status == 'PRESENT' ? 'fa-check-circle' : 'fa-times-circle'}"></i>
                                    ${record.status}
                                </span>
                            </td>
                            <td>${record.date}</td>
                            <td>${record.subjectName}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="form-group" style="padding-top: 2.2rem;">
                            <a href="${pageContext.request.contextPath}/teacher/dashboard" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i>
                                Back to Dashboard
                            </a>
         </div>
    </div>

    <script>
        // Initialize attendance chart
        const ctx = document.getElementById('attendanceChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Present', 'Absent'],
                datasets: [{
                    label: 'Attendance Overview',
                    data: [${presentCount}, ${absentCount}],
                    backgroundColor: [
                        'rgba(76, 175, 80, 0.5)',
                        'rgba(244, 67, 54, 0.5)'
                    ],
                    borderColor: [
                        'rgba(76, 175, 80, 1)',
                        'rgba(244, 67, 54, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            color: '#ffffff'
                        }
                    },
                    x: {
                        ticks: {
                            color: '#ffffff'
                        }
                    }
                },
                plugins: {
                    legend: {
                        labels: {
                            color: '#ffffff'
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>
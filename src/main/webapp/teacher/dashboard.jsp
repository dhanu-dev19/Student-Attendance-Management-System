<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Dashboard - Attendance Management System</title>
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
            background: linear-gradient(45deg, #ff6b6b, #ffd93d);
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
        .nav-links a:hover:after {
            width: 100%;
        }
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        .welcome-section {
            text-align: center;
            margin-bottom: 2rem;
        }
        .welcome-section h2 {
            font-size: 2.8rem;
            font-weight: 700;
            background: linear-gradient(45deg, #a1ffce, #faffd1);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            margin-bottom: 0.5rem;
        }
        .welcome-section p {
            font-size: 1.2rem;
            color: rgba(255,255,255,0.8);
        }
        .stats-container {
            display: flex;
            gap: 2rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
            justify-content: center;
        }
        .stat-card {
            flex: 1 1 220px;
            background: rgba(255,255,255,0.13);
            border-radius: 24px;
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.18);
            padding: 2.5rem 1.5rem;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 1rem;
            transition: box-shadow 0.2s, transform 0.2s;
            backdrop-filter: blur(8px);
        }
        .stat-card:hover {
            box-shadow: 0 12px 40px 0 rgba(255, 107, 107, 0.18);
            transform: translateY(-4px) scale(1.03);
        }
        .stat-card i {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
            color: #ffd93d;
            filter: drop-shadow(0 2px 8px #ff6b6b33);
        }
        .stat-card h3 {
            margin: 0.5rem 0 0.2rem 0;
            font-size: 1.2rem;
            font-weight: 600;
            color: #fff;
        }
        .stat-number {
            font-size: 2.5rem;
            font-weight: 700;
            color: #fff;
            text-shadow: 0 2px 10px #ff6b6b33;
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
        .btn {
            background: linear-gradient(45deg, #ff6b6b, #ffd93d);
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
            box-shadow: 0 4px 15px rgba(255, 107, 107, 0.15);
            transition: background 0.2s, box-shadow 0.2s, transform 0.2s;
            margin-right: 0.5rem; /* Space between buttons */
            margin-bottom: 0.5rem; /* For wrapping on smaller screens */
        }
        .btn:hover {
            background: linear-gradient(45deg, #ffd93d, #ff6b6b);
            color: #222;
            transform: translateY(-2px) scale(1.02);
            box-shadow: 0 8px 24px rgba(255, 107, 107, 0.25);
        }
        /* Specific small button style if needed for table actions */
        .small-btn {
            padding: 0.5rem 1rem;
            font-size: 0.8rem;
            gap: 0.4rem;
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
            background: rgba(255,255,255,0.15); /* Slightly lighter transparent white on hover */
        }

        /* Remove last border-bottom for aesthetic */
        .table tbody tr:last-child td {
            border-bottom: none;
        }
        .status-absent {
            color: #ff6b6b;
            font-weight: 700;
        }
        .no-data {
            text-align: center;
            padding: 2rem;
            color: rgba(255,255,255,0.7);
            font-style: italic;
        }
        .footer {
            text-align: center;
            padding: 1.5rem;
            margin-top: 3rem;
            color: rgba(255,255,255,0.6);
            font-size: 0.9rem;
            background: rgba(255,255,255,0.05);
            backdrop-filter: blur(5px);
            box-shadow: 0 -2px 10px rgba(0,0,0,0.1);
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
            .stats-container {
                flex-direction: column;
                gap: 1.5rem;
            }
            .stat-card {
                flex: 1 1 auto;
            }
            .table {
                overflow-x: auto;
                display: block;
                white-space: nowrap; /* Prevent content from wrapping */
            }
            .table thead, .table tbody, .table th, .table td, .table tr {
                display: block;
            }
            .table th, .table td {
                width: auto !important; /* Override explicit width for responsive */
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
            <h1>Teacher Dashboard</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/teacher/dashboard"><i class="fas fa-home"></i> Dashboard</a>
                <a href="${pageContext.request.contextPath}/teacher/mark-attendance"><i class="fas fa-marker"></i> Mark Attendance</a>
                <a href="${pageContext.request.contextPath}/teacher/view-attendance"><i class="fas fa-eye"></i> View Attendance</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
            </div>
        </nav>
    </header>

    <div class="container">
        <div class="welcome-section card">
            <h2>Welcome, ${currentTeacher.name}!</h2>
            <p>Your comprehensive overview of subjects, students, and attendance.</p>
        </div>

        <div class="stats-container">
            <div class="stat-card">
                <i class="fas fa-book"></i>
                <h3>Total Subjects</h3>
                <p class="stat-number">${totalSubjects}</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-user-graduate"></i>
                <h3>Total Students</h3>
                <p class="stat-number">${totalStudents}</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-chart-line"></i>
                <h3>Average Attendance</h3>
                <p class="stat-number">${averageAttendance}%</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-bell"></i>
                <h3>Low Attendance Alerts</h3>
                <p class="stat-number">${lowAttendanceCount}</p>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>My Subjects</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th data-label="Subject Name">Subject Name</th>
                        <th data-label="Total Students">Total Students</th>
                        <th data-label="Average Attendance">Average Attendance</th>
                        <th data-label="Actions">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty subjects}">
                            <c:forEach items="${subjects}" var="subject">
                                <tr>
                                    <td data-label="Subject Name">${subject.name}</td>
                                    <td data-label="Total Students">${subject.totalStudents}</td>
                                    <td data-label="Average Attendance">${subject.averageAttendance}%</td>
                                    <td data-label="Actions">
                                        <a href="${pageContext.request.contextPath}/teacher/mark-attendance?subjectId=${subject.id}"
                                           class="btn small-btn">
                                            <i class="fas fa-marker"></i> Mark Attendance
                                        </a>
                                        <a href="${pageContext.request.contextPath}/teacher/view-attendance?subjectId=${subject.id}"
                                           class="btn small-btn">
                                            <i class="fas fa-eye"></i> View Report
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4" class="no-data">No subjects assigned yet.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <div class="card">
            <div class="card-header">
                <h2>Attendance Overview</h2>
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
                <h2>Low Attendance Alerts</h2>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th data-label="Student">Student</th>
                        <th data-label="Subject">Subject</th>
                        <th data-label="Attendance %">Attendance %</th>
                        <th data-label="Action">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty lowAttendanceAlerts}">
                            <c:forEach items="${lowAttendanceAlerts}" var="alert">
                                <tr>
                                    <td data-label="Student">${alert.studentName}</td>
                                    <td data-label="Subject">${alert.subjectName}</td>
                                    <td data-label="Attendance %" class="status-absent">${alert.attendancePercentage}%</td>
                                    <td data-label="Action">
                                        <a href="${pageContext.request.contextPath}/teacher/view-attendance?studentId=${alert.studentId}&subjectId=${alert.subjectId}"
                                           class="btn small-btn">
                                            <i class="fas fa-info-circle"></i> View Details
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4" class="no-data">No low attendance alerts at this time.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <c:if test="${not empty param.studentId && not empty param.subjectId}">
                    <div class="card">
                        <div class="card-header">
                            <h2>
                                Attendance Details for
                                <c:out value="${attendanceRecords[0].studentName}" />
                                in <c:out value="${attendanceRecords[0].subjectName}" />
                            </h2>
                        </div>
                        <c:choose>
                            <c:when test="${not empty attendanceRecords}">
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
                                                <td><c:out value="${record.date}" /></td>
                                                <td>
                                                    <span class="${record.status == 'PRESENT' ? 'status-present' : 'status-absent'}">
                                                        <c:out value="${record.status}" />
                                                    </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p class="no-data">No attendance records found for this student and subject.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
    </div>

    <footer class="footer">
        <p>&copy; 2025 Attendance Management System. All rights reserved.</p>
    </footer>

  <script>
      // Initialize attendance chart
      const ctx = document.getElementById('attendanceChart').getContext('2d');

      // Register plugin for background and text contrast
      Chart.register({
          id: 'customCanvasBackground',
          beforeDraw: (chart) => {
              const {ctx, chartArea: {left, top, width, height}} = chart;
              ctx.save();
              ctx.fillStyle = 'white';
              ctx.fillRect(left, top, width, height);
              ctx.restore();
          }
      });

      const attendanceData = {
          labels: JSON.parse(`${attendanceDates}`),
          datasets: [{
              label: 'Attendance Percentage',
              data: JSON.parse(`${attendancePercentages}`),
              borderColor: '#1e88e5',  // Changed to high-contrast blue
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
      };

      const chartConfig = {
          type: 'line',
          data: attendanceData,
          options: {
              responsive: true,
              plugins: {
                  legend: {
                      labels: {
                          font: {
                              size: 14,
                              weight: 'bold'  // Bold legend text
                          },
                          color: '#263238'  // Dark text color
                      }
                  },
                  tooltip: {
                      backgroundColor: 'rgba(38, 50, 56, 0.9)',  // Dark tooltip
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
                          color: 'rgba(0,0,0,0.08)'  // Lighter gridlines
                      },
                      ticks: {
                          color: '#fff',  // Dark text
                          font: {
                              size: 13,
                              weight: 'bold'
                          },
                          callback: function(value) {
                              return value + '%';  // Add percentage sign
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
                          display: false  // Remove x-axis gridlines
                      },
                      ticks: {
                          color: '#fff',  // Dark text
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
                      borderWidth: 3  // Thicker line
                  }
              }
          },
          plugins: ['customCanvasBackground']
      };

      new Chart(ctx, chartConfig);
  </script>
</body>
</html>
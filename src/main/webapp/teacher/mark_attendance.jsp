<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mark Attendance - Teacher Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

        <style>
            body {
                background: #2b2d42; /* A dark gray/blue for the background */
                color: #e0e0e0; /* Light color for general text, like the main h1 */
                font-family: 'Poppins', sans-serif; /* Ensure font is applied */
                min-height: 100vh; /* Ensure it covers full height */
                margin: 0;
                padding: 0;
                display: flex; /* For potential centering */
                flex-direction: column;
                align-items: center; /* Center horizontally */
                justify-content: flex-start; /* Align to top, push content down */
                padding-top: 50px; /* Add some top padding */
            }

            .container {
                width: 90%;
                max-width: 1000px;
                margin: 0 auto; /* Center the container */
            }

            h1 {
                color: #ffffff; /* Ensure main heading is white */
                margin-bottom: 2rem;
                text-align: center;
            }

            .attendance-form, .card {
                background: rgba(255, 255, 255, 0.08); /* Darker transparent white for glassmorphism */
                backdrop-filter: blur(12px);
                border-radius: 20px;
                padding: 2.5rem;
                box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
                margin-bottom: 2.5rem;
                border: 1px solid rgba(255, 255, 255, 0.1);
            }
            .form-group {
                margin-bottom: 1.5rem;
            }
            .form-group label {
                display: block;
                margin-bottom: 0.5rem;
                color: #e0e0e0; /* Light color for labels */
                font-weight: 500;
            }
            .form-control {
                width: 100%;
                padding: 0.75rem;
                border: 1px solid rgba(255, 255, 255, 0.2);
                border-radius: 8px;
                background: rgba(255, 255, 255, 0.1); /* Slightly darker background for form controls */
                color: #ffffff; /* White text for input/select values */
                font-size: 1rem;
            }
            .form-control:focus {
                outline: none;
                border-color: #4a90e2;
            }
            /* Placeholder color for selects */
            .form-control option:first-child {
                color: #a0a0a0; /* Grey for placeholder text */
            }
            .form-control option {
                background: #2b2d42; /* Dark background for dropdown options */
                color: #ffffff; /* White text for actual options */
            }
            .filter-form .form-row {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 1.5rem;
                margin-bottom: 2rem;
            }

            .form-actions {
                display: flex;
                justify-content: flex-end;
                padding-top: 1rem;
                border-top: 1px solid rgba(255, 255, 255, 0.1);
                margin-top: 1.5rem;
            }

            .form-actions .btn-submit {
                width: auto;
                padding: 0.75rem 2rem;
            }
            .attendance-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 1rem;
            }
            .attendance-table th, .attendance-table td {
                padding: 1rem;
                text-align: left;
                border-bottom: 1px solid rgba(255, 255, 255, 0.1);
                color: #e0e0e0; /* Ensure table text is visible */
            }
            .attendance-table th {
                background: rgba(255, 255, 255, 0.05);
                color: #fff;
                font-weight: 600;
            }
            .attendance-table tr:hover {
                background: rgba(255, 255, 255, 0.05);
            }
            .btn-submit {
                background: linear-gradient(135deg, #4a90e2, #357abd);
                color: white;
                border: none;
                padding: 0.75rem 1.5rem;
                border-radius: 8px;
                cursor: pointer;
                font-size: 1rem;
                font-weight: 500;
                transition: all 0.3s ease;
            }
            .btn-submit:hover {
                background: linear-gradient(135deg, #357abd, #2a5f9e);
                transform: translateY(-2px);
            }
        </style>
</head>
<body>
    <div class="container">
        <h1>Mark Attendance</h1>
        <div class="attendance-form">
            <form action="${pageContext.request.contextPath}/teacher/mark-attendance" method="get" class="filter-form">
                <div class="form-row">
                    <div class="form-group">
                        <label for="subject">Select Subject</label>
                        <select name="subject" id="subject" class="form-control" required>
                            <option value="">Select a subject</option>
                            <c:forEach items="${subjects}" var="subject">
                                <option value="${subject.id}" ${param.subject == subject.id ? 'selected' : ''}>${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="branch">Select Branch</label>
                        <select name="branch" id="branch" class="form-control" required>
                            <option value="">Select a branch</option>
                            <c:forEach items="${branches}" var="branch">
                                <option value="${branch}" ${param.branch == branch ? 'selected' : ''}>${branch}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="year">Select Year</label>
                        <select name="year" id="year" class="form-control" required>
                            <option value="">Select a year</option>
                            <c:forEach items="${years}" var="year">
                                <option value="${year}" ${param.year == year ? 'selected' : ''}>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="date">Date</label>
                        <input type="date" name="date" id="date" class="form-control" value="${param.date}" required>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn-submit">Get Students</button>
                </div>
            </form>
        </div>

        <c:if test="${not empty students}">
            <div class="card">
                <div class="card-header">
                    <h2>Students for Attendance</h2>
                </div>
                <form action="${pageContext.request.contextPath}/teacher/mark-attendance" method="post">
                    <input type="hidden" name="subject" value="${param.subject}">
                    <input type="hidden" name="branch" value="${param.branch}">
                    <input type="hidden" name="year" value="${param.year}">
                    <input type="hidden" name="date" value="${param.date}">
                    <table class="attendance-table">
                        <thead>
                            <tr>
                                <th>Student ID</th>
                                <th>Name</th>
                                <th>Branch</th>
                                <th>Year</th>
                                <th>Attendance</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${students}" var="student">
                                <tr>
                                    <td>${student.id}</td>
                                    <td>${student.name}</td>
                                    <td>${student.branch}</td>
                                    <td>${student.year}</td>
                                    <td>
                                        <select name="attendance_${student.id}" class="form-control" required>
                                            <option value="present">Present</option>
                                            <option value="absent">Absent</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="form-actions">
                        <button type="submit" class="btn-submit">Mark Attendance</button>
                    </div>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>
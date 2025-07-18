<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance Management System</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #0f0c29 0%, #302b63 50%, #24243e 100%);
            font-family: 'Poppins', sans-serif;
            color: #ffffff;
            min-height: 100vh;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .container {
            width: 100%;
            max-width: 1400px;
            padding: 2rem;

        }

        .login-container {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 3rem;
            box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
            border: 1px solid rgba(255, 255, 255, 0.1);
            text-align: center;
        }

        .welcome-section {
            margin-bottom: 3rem;
        }

        h1 {
            font-size: 3rem;
            font-weight: 700;
            margin-bottom: 1rem;
            background: linear-gradient(45deg, #ff6b6b, #ffd93d);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            text-shadow: 0 2px 10px rgba(255, 107, 107, 0.3);
        }

        .subtitle {
            font-size: 1.2rem;
            color: #b8b8b8;
            margin-bottom: 2rem;
        }

        .features {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 2rem;
            margin-bottom: 3rem;
        }

        .feature-card {
            background: rgba(255, 255, 255, 0.05);
            padding: 1.5rem;
            border-radius: 15px;
            transition: transform 0.3s ease;
        }

        .feature-card:hover {
            transform: translateY(-5px);
        }

        .feature-icon {
            font-size: 2rem;
            margin-bottom: 1rem;
            color: #ff6b6b;
        }

        .feature-title {
            font-size: 1.2rem;
            font-weight: 600;
            margin-bottom: 0.5rem;
            color: #ffffff;
        }

        .feature-description {
            font-size: 0.9rem;
            color: #b8b8b8;
        }

        .login-options {
            display: flex;
            justify-content: center;
            gap: 1.5rem;
            flex-wrap: wrap;
        }

        .btn {
            padding: 1rem 2.5rem;
            border-radius: 50px;
            font-size: 1.1rem;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            min-width: 200px;
            display: flex;
            align-items: center;
            justify-content: center;
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

        .btn-primary:active {
            transform: translateY(0);
        }

        .btn i {
            font-size: 1.2rem;
        }

        .footer {
            margin-top: 3rem;
            color: #b8b8b8;
            font-size: 0.9rem;
        }

        @media (max-width: 768px) {
            .container {
                padding: 1rem;
            }

            .login-container {
                padding: 2rem;
            }

            h1 {
                font-size: 2rem;
            }

            .features {
                grid-template-columns: 1fr;
            }

            .login-options {
                flex-direction: column;
            }

            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="login-container">
            <div class="welcome-section">
                <h1>Welcome to Attendance Management System</h1>
                <p class="subtitle">Streamline your attendance tracking with our modern solution</p>
            </div>

            <div class="features">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-chart-line"></i>
                    </div>
                    <h3 class="feature-title">Real-time Analytics</h3>
                    <p class="feature-description">Track attendance patterns and generate insightful reports instantly</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-mobile-alt"></i>
                    </div>
                    <h3 class="feature-title">Mobile Friendly</h3>
                    <p class="feature-description">Access the system from any device, anywhere, anytime</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-shield-alt"></i>
                    </div>
                    <h3 class="feature-title">Secure Platform</h3>
                    <p class="feature-description">Your data is protected with enterprise-grade security</p>
                </div>
            </div>

            <div class="login-options">
                <a href="<%= request.getContextPath() %>/login.jsp?role=admin" class="btn btn-primary">
                    <i class="fas fa-user-shield"></i>
                    Admin Login
                </a>
                <a href="<%= request.getContextPath() %>/login.jsp?role=teacher" class="btn btn-primary">
                    <i class="fas fa-chalkboard-teacher"></i>
                    Teacher Login
                </a>
                <a href="<%= request.getContextPath() %>/login.jsp?role=student" class="btn btn-primary">
                    <i class="fas fa-user-graduate"></i>
                    Student Login
                </a>
            </div>

            <div class="footer">
                <p>© 2024 Attendance Management System. All rights reserved.</p>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>
</html>


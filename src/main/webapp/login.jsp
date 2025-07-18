<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Attendance Management System</title>
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
            max-width: 1200px;
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
            max-width: 550px;
            margin: 0 auto;
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

        .form {
            display: flex;
            flex-direction: column;
            justify-content: center;
            width: 100%;
        }

        .form-group {
            text-align: left;
            position: relative;
            width: 100%;
            max-width: 500px;
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #ffffff;
            font-weight: 500;
            font-size: 1.1rem;
        }

        .form-control {
            width: 100%;
            padding: 1rem 1.5rem;
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

        .form-control::placeholder {
            color: rgba(255, 255, 255, 0.5);
        }

        .btn {
            padding: 1rem 2rem;
            border-radius: 50px;
            font-size: 1.1rem;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            width: 80%;
            max-width: 300px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.5rem;
            margin: 0 auto;
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

        .role-badge {
            display: inline-block;
            padding: 0.5rem 1rem;
            background: linear-gradient(45deg, #ff6b6b, #ffd93d);
            border-radius: 50px;
            font-size: 0.9rem;
            font-weight: 500;
            margin-bottom: 2rem;
            text-transform: capitalize;
        }

        .form-footer {
            margin-top: 2rem;
            color: #b8b8b8;
            font-size: 0.9rem;
        }

        .form-buttons {
            display: flex;
            flex-direction: column;
            gap: 1rem;
            align-items: center;
            width: 100%;
            margin: 0 auto;
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

            .form-control {
                padding: 0.8rem 1.2rem;
            }

            .btn {
                width: 90%;
                max-width: none;
                padding: 1rem 2rem;
                margin: 0 auto;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="login-container">
            <c:choose>
                <c:when test="${not empty param.role}">
                    <div class="role-badge">
                        <i class="fas ${param.role == 'admin' ? 'fa-user-shield' : param.role == 'teacher' ? 'fa-chalkboard-teacher' : 'fa-user-graduate'}"></i>
                        ${param.role} Login
                    </div>
                    <h1>Welcome Back</h1>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-circle"></i>
                            ${error}
                        </div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/${param.role}/login" method="post" class="form">
                        <input type="hidden" name="role" value="${param.role}">
                        <div class="form-group">
                            <label for="email">
                                <i class="fas fa-envelope"></i>
                                Email Address
                            </label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
                        </div>
                        <div class="form-group">
                            <label for="password">
                                <i class="fas fa-lock"></i>
                                Password
                            </label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
                        </div>
                        <div class="form-buttons">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-sign-in-alt"></i>
                                Login
                            </button>
                            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i>
                                Back to Home
                            </a>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="role-badge">
                        <i class="fas fa-info-circle"></i>
                        Role Not Selected
                    </div>
                    <h1>Select a Role to Login</h1>
                    <div class="form-buttons">
                        <a href="${pageContext.request.contextPath}/login.jsp?role=admin" class="btn btn-primary">
                            <i class="fas fa-user-shield"></i>
                            Admin Login
                        </a>
                        <a href="${pageContext.request.contextPath}/login.jsp?role=teacher" class="btn btn-primary">
                            <i class="fas fa-chalkboard-teacher"></i>
                            Teacher Login
                        </a>
                        <a href="${pageContext.request.contextPath}/login.jsp?role=student" class="btn btn-primary">
                            <i class="fas fa-user-graduate"></i>
                            Student Login
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="form-footer">
                <p>Having trouble? Contact your administrator for support.</p>
            </div>
        </div>
    </div>
</body>
</html>
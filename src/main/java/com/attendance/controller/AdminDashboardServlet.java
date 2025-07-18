package com.attendance.controller;

import com.attendance.dao.StudentDAO;
import com.attendance.dao.TeacherDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.dao.AttendanceDAO;
import com.attendance.model.Student;
import com.attendance.model.Teacher;
import com.attendance.model.Subject;
import com.attendance.model.Attendance;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private StudentDAO studentDAO;
    private TeacherDAO teacherDAO;
    private SubjectDAO subjectDAO;
    private AttendanceDAO attendanceDAO;

    public void init() {
        studentDAO = new StudentDAO();
        teacherDAO = new TeacherDAO();
        subjectDAO = new SubjectDAO();
        attendanceDAO = new AttendanceDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get total counts
            int totalStudents = studentDAO.getTotalStudents();
            int totalTeachers = teacherDAO.getTotalTeachers();
            int totalSubjects = subjectDAO.getTotalSubjects();

            // Get average attendance percentage
            double averageAttendance = attendanceDAO.getOverallAverageAttendance();

            // Get daily attendance overview for chart
            Map<String, Double> attendanceOverview = attendanceDAO.getDailyAttendanceOverview();
            List<String> attendanceOverviewDates = new java.util.ArrayList<>(attendanceOverview.keySet());
            List<Double> attendanceOverviewPercentages = new java.util.ArrayList<>(attendanceOverview.values());

            // Get recent activities (attendance records)
            List<Map<String, Object>> recentActivities = attendanceDAO.getRecentActivities(10);

            // Get low attendance alerts (with teacher phone)
            List<Map<String, Object>> lowAttendanceAlerts = attendanceDAO.getLowAttendanceAlerts(75.0); // 75% threshold

            // Convert to JSON for JavaScript in JSP
            Gson gson = new Gson();
            request.setAttribute("attendanceDatesJson", gson.toJson(attendanceOverviewDates));
            request.setAttribute("attendancePercentagesJson", gson.toJson(attendanceOverviewPercentages));

            // Set attributes for the JSP
            request.setAttribute("totalStudents", totalStudents);
            request.setAttribute("totalTeachers", totalTeachers);
            request.setAttribute("totalSubjects", totalSubjects);
            request.setAttribute("averageAttendance", averageAttendance);
            request.setAttribute("recentActivities", recentActivities);
            request.setAttribute("lowAttendanceAlerts", lowAttendanceAlerts);

            // Forward to the dashboard JSP
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error loading dashboard data", e);
        }
    }
}



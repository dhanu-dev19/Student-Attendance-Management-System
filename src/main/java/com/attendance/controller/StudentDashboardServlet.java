package com.attendance.controller;

import com.attendance.dao.AttendanceDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.model.Attendance;
import com.attendance.model.Student;
import com.attendance.model.Subject;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SubjectDAO subjectDAO;
    private AttendanceDAO attendanceDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        subjectDAO = new SubjectDAO();
        attendanceDAO = new AttendanceDAO();
        gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student currentStudent = (Student) session.getAttribute("student");

        if (currentStudent == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int studentId = currentStudent.getId();

        try {
            // Fetch dashboard statistics
            int totalSubjects = subjectDAO.getTotalSubjectsByStudent(studentId);
            double overallAttendance = attendanceDAO.getStudentOverallAverageAttendance(studentId);
            int presentDays = attendanceDAO.getStudentPresentDays(studentId);
            int absentDays = attendanceDAO.getStudentAbsentDays(studentId);

            // Fetch subject-wise attendance
            List<Subject> subjects = subjectDAO.getSubjectsByStudentId(studentId);
            for (Subject subject : subjects) {
                subject.setAttendancePercentage(attendanceDAO.getSubjectAttendancePercentageForStudent(subject.getId(), studentId));
            }

            // Fetch data for attendance overview chart
            Map<String, Double> attendanceOverview = attendanceDAO.getStudentAttendanceOverview(studentId);
            List<String> attendanceDates = attendanceOverview.keySet().stream().collect(Collectors.toList());
            List<Double> attendancePercentages = attendanceOverview.values().stream().collect(Collectors.toList());

            // Convert lists to JSON strings using Gson and escape them for JavaScript
            String jsonAttendanceDates = StringEscapeUtils.escapeEcmaScript(gson.toJson(attendanceDates));
            String jsonAttendancePercentages = StringEscapeUtils.escapeEcmaScript(gson.toJson(attendancePercentages));

            // Set attributes for JSP
            request.setAttribute("totalSubjects", totalSubjects);
            request.setAttribute("overallAttendance", String.format("%.2f", overallAttendance));
            request.setAttribute("presentDays", presentDays);
            request.setAttribute("absentDays", absentDays);
            request.setAttribute("subjectAttendance", subjects);
            request.setAttribute("attendanceDates", jsonAttendanceDates);
            request.setAttribute("attendancePercentages", jsonAttendancePercentages);

            // Get recent attendance records
            List<Attendance> recentAttendance = attendanceDAO.getRecentAttendanceByStudent(studentId, 5);
            request.setAttribute("recentAttendance", recentAttendance);

            request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading student dashboard: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}

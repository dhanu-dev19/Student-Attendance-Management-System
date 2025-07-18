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
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

//@WebServlet("/student/view_attendance")
public class StudentViewAttendanceServlet extends HttpServlet {
    private AttendanceDAO attendanceDAO;
    private SubjectDAO subjectDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        attendanceDAO = new AttendanceDAO();
        subjectDAO = new SubjectDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        int studentId = student.getId();

        try {
            // Fetch all subjects for the student
            List<Subject> subjects = subjectDAO.getSubjectsByStudentId(studentId);
            request.setAttribute("subjects", subjects);

            // Get filter parameters
            String subjectIdStr = request.getParameter("subjectId");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");

            Integer subjectId = (subjectIdStr != null && !subjectIdStr.isEmpty()) ? Integer.parseInt(subjectIdStr) : null;
            Date startDate = (startDateStr != null && !startDateStr.isEmpty()) ? Date.valueOf(startDateStr) : null;
            Date endDate = (endDateStr != null && !endDateStr.isEmpty()) ? Date.valueOf(endDateStr) : null;

            // Fetch attendance records with filters
            List<Attendance> attendanceRecords = attendanceDAO.getAttendanceByStudentWithFilters(studentId, subjectId, startDate, endDate);
            request.setAttribute("attendanceRecords", attendanceRecords);

            // Attendance overview chart data (date-wise attendance %)
            Map<String, Double> attendanceOverview = attendanceDAO.getStudentAttendanceOverview(studentId, subjectId, startDate, endDate);
            List<String> attendanceDates = new ArrayList<>(attendanceOverview.keySet());
            List<Double> attendancePercentages = new ArrayList<>(attendanceOverview.values());
            String jsonAttendanceDates = StringEscapeUtils.escapeEcmaScript(gson.toJson(attendanceDates));
            String jsonAttendancePercentages = StringEscapeUtils.escapeEcmaScript(gson.toJson(attendancePercentages));
            request.setAttribute("attendanceDates", jsonAttendanceDates);
            request.setAttribute("attendancePercentages", jsonAttendancePercentages);

            // Subject-wise summary
            List<Map<String, Object>> subjectSummary = attendanceDAO.getStudentSubjectSummary(studentId);
            request.setAttribute("subjectSummary", subjectSummary);

            request.getRequestDispatcher("/student/view_attendance.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading attendance data: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}


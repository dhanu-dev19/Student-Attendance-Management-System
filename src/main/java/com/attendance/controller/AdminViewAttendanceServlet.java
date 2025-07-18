package com.attendance.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.attendance.model.Attendance;
import com.attendance.model.Student;
import com.google.gson.Gson;
import com.attendance.dao.AttendanceDAO;
import com.attendance.dao.StudentDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.model.Subject;

//@WebServlet("/admin/attendance/view")
public class AdminViewAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AttendanceDAO attendanceDAO;
    private StudentDAO studentDAO;
    private SubjectDAO subjectDAO;

    @Override
    public void init() throws ServletException {
        attendanceDAO = new AttendanceDAO();
        studentDAO = new StudentDAO();
        subjectDAO = new SubjectDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentIdStr = request.getParameter("studentId");
        String subjectIdStr = request.getParameter("subjectId");

        if (studentIdStr != null && subjectIdStr != null) {
            // Show attendance details for a student in a subject
            int studentId = Integer.parseInt(studentIdStr);
            int subjectId = Integer.parseInt(subjectIdStr);

            Student student = studentDAO.getStudentById(studentId);
            Subject subject = null;
            try {
                subject = subjectDAO.getSubjectById(subjectId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            List<Attendance> attendanceRecord = attendanceDAO.getAttendanceByStudentAndSubject(studentId, subjectId);

            request.setAttribute("student", student);
            request.setAttribute("subject", subject);
            request.setAttribute("attendanceRecords", attendanceRecord);

            request.getRequestDispatcher("/admin/attendance_details.jsp").forward(request, response);
            return; // IMPORTANT: return after forwarding!
        }

        // Otherwise, show the main attendance overview page
        String subjectId = request.getParameter("subjectId");
        String className = request.getParameter("className");
        String branch = request.getParameter("branch");
        String year = request.getParameter("year");
        String date = request.getParameter("date");

        List<Map<String, Object>> attendanceRecords = attendanceDAO.getAttendanceRecords(subjectId, className, branch, year, date);
        Map<String, Double> attendanceOverview = attendanceDAO.getAttendanceOverview(subjectId, className, branch, year);
        List<Map<String, Object>> lowAttendanceAlerts = null;
        try {
            lowAttendanceAlerts = attendanceDAO.getLowAttendanceAlerts(75.0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Subject> subjects = null;
        try {
            subjects = subjectDAO.getAllSubjects();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> classNames = studentDAO.getAllClassNames();
        List<String> branches = studentDAO.getAllBranches();
        List<Integer> years = studentDAO.getAllYears();

        Gson gson = new Gson();
        request.setAttribute("attendanceDates", gson.toJson(attendanceOverview.keySet()));
        request.setAttribute("attendancePercentages", gson.toJson(attendanceOverview.values()));
        request.setAttribute("subjects", subjects);
        request.setAttribute("classes", classNames);
        request.setAttribute("branches", branches);
        request.setAttribute("years", years);
        request.setAttribute("attendanceRecords", attendanceRecords);
        request.setAttribute("lowAttendanceAlerts", lowAttendanceAlerts);

        request.getRequestDispatcher("/admin/view_attendance.jsp").forward(request, response);
    }
}


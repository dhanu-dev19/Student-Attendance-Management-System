package com.attendance.controller;

import com.attendance.dao.AttendanceDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.model.Attendance;
import com.attendance.dao.StudentDAO;
import com.attendance.model.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@WebServlet("/teacher/view-attendance")
public class ViewAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AttendanceDAO attendanceDAO;
    private SubjectDAO subjectDAO;
    private StudentDAO studentDAO;

    public void init() {
        attendanceDAO = new AttendanceDAO();
        subjectDAO = new SubjectDAO();
        studentDAO = new StudentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("teacherId");

        if (teacherId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Get all subjects taught by this teacher
            List<Subject> subjects = subjectDAO.getSubjectsByTeacherId(teacherId);
            List<String> branches = studentDAO.getUniqueBranches();
            List<Integer> years = studentDAO.getUniqueYears();
            request.setAttribute("subjects", subjects);
            request.setAttribute("branches", branches);
            request.setAttribute("years", years);

            // Get filter parameters
            String subjectIdStr = request.getParameter("subjectId");
            String studentIdStr = request.getParameter("studentId");
            String dateStr = request.getParameter("date");

            List<Attendance> attendanceRecords = new ArrayList<>();
            int presentCount = 0;
            int absentCount = 0;

            if (subjectIdStr != null && !subjectIdStr.isEmpty() && studentIdStr != null && !studentIdStr.isEmpty()) {
                // Filter by subject and student
                int subjectId = Integer.parseInt(subjectIdStr);
                int studentId = Integer.parseInt(studentIdStr);
                attendanceRecords = attendanceDAO.getAttendanceByStudentAndSubject(studentId, subjectId);
            } else if (subjectIdStr != null && !subjectIdStr.isEmpty() && dateStr != null && !dateStr.isEmpty()) {
                // Filter by subject and date
                int subjectId = Integer.parseInt(subjectIdStr);
                Date date = Date.valueOf(dateStr);
                attendanceRecords = attendanceDAO.getAttendanceBySubjectAndDate(subjectId, date);
            } else {
                // Get all attendance records for teacher's subjects
                for (Subject subject : subjects) {
                    attendanceRecords.addAll(attendanceDAO.getAttendanceBySubject(subject.getId()));
                }
            }

            // Calculate present and absent counts
            presentCount = (int) attendanceRecords.stream()
                    .filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus()))
                    .count();
            absentCount = (int) attendanceRecords.stream()
                    .filter(a -> "ABSENT".equalsIgnoreCase(a.getStatus()))
                    .count();

            // Set attributes for JSP
            request.setAttribute("attendanceRecords", attendanceRecords);
            request.setAttribute("presentCount", presentCount);
            request.setAttribute("absentCount", absentCount);

            // Forward to JSP
            request.getRequestDispatcher("/teacher/view_attendance.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error retrieving attendance data: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}


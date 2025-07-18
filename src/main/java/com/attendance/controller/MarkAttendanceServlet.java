package com.attendance.controller;

import com.attendance.dao.StudentDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.dao.AttendanceDAO;
import com.attendance.model.Student;
import com.attendance.model.Subject;
import com.attendance.model.Attendance;
import com.attendance.util.DBConnection;
import com.attendance.util.EmailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@WebServlet("/teacher/mark-attendance")
public class MarkAttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;
    private SubjectDAO subjectDAO;
    private AttendanceDAO attendanceDAO;
    private EmailUtil emailUtil;

    public void init() {
        studentDAO = new StudentDAO();
        subjectDAO = new SubjectDAO();
        attendanceDAO = new AttendanceDAO();
        emailUtil = new EmailUtil();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("teacherId");

        if (teacherId == null) {
            response.sendRedirect(request.getContextPath() + "/teacher/login");
            return;
        }

        try {
            // Get unique branches and years from student table
            String branchQuery = "SELECT DISTINCT branch FROM student ORDER BY branch";
            String yearQuery = "SELECT DISTINCT year FROM student ORDER BY year";

            // Always get unique branches and years for the filter dropdowns
            List<String> branches = studentDAO.getUniqueBranches();
            List<Integer> years = studentDAO.getUniqueYears();
            request.setAttribute("branches", branches);
            request.setAttribute("years", years);

            // Always get subjects for the teacher for the filter dropdown
            List<Subject> subjects = subjectDAO.getSubjectsByTeacherId(teacherId);
            request.setAttribute("subjects", subjects);

            // Check if filter parameters are present (meaning the form was submitted via GET)
            String subjectParam = request.getParameter("subject");
            String branchParam = request.getParameter("branch");
            String yearParam = request.getParameter("year");
            String dateParam = request.getParameter("date");

            if (subjectParam != null && !subjectParam.isEmpty() &&
                    branchParam != null && !branchParam.isEmpty() &&
                    yearParam != null && !yearParam.isEmpty() &&
                    dateParam != null && !dateParam.isEmpty()) {

                // Fetch students based on selected branch and year
                // SubjectId is used in the POST method to record attendance against a subject
                int year = Integer.parseInt(yearParam);
                List<Student> students = studentDAO.getStudentsByBranchAndYear(branchParam, year);
                request.setAttribute("students", students);

                // Keep selected subject, branch, year, date values in request for JSP to pre-fill form
                request.setAttribute("selectedSubjectId", subjectParam);
                request.setAttribute("selectedBranch", branchParam);
                request.setAttribute("selectedYear", yearParam);
                request.setAttribute("selectedDate", dateParam);
            }

            // Forward to the mark attendance page
            request.getRequestDispatcher("/teacher/mark_attendance.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error retrieving data: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    // src/main/java/com/attendance/controller/MarkAttendanceServlet.java

// ... (imports and init method) ...

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer teacherId = (Integer) session.getAttribute("teacherId");

        if (teacherId == null) {
            response.sendRedirect(request.getContextPath() + "/teacher/login");
            return;
        }

        try {
            int subjectId = Integer.parseInt(request.getParameter("subject"));
            String branch = request.getParameter("branch");
            int year = Integer.parseInt(request.getParameter("year"));
            String date = request.getParameter("date");

            // Get subject details for email notification (still needed for attendance record)
            Subject subject = subjectDAO.getSubjectById(subjectId);

            // Fetch students based on branch and year (as per existing student table structure)
            List<Student> students = studentDAO.getStudentsByBranchAndYear(branch, year); // <--- CHANGED HERE

            // Process attendance for each student
            for (Student student : students) {
                String attendanceStatus = request.getParameter("attendance_" + student.getId());

                if (attendanceStatus != null) {
                    Attendance attendance = new Attendance();
                    attendance.setStudentId(student.getId());
                    attendance.setSubjectId(subjectId); // Subject ID is still used in attendance record
                    attendance.setDate(java.sql.Date.valueOf(date));
                    attendance.setStatus(attendanceStatus.toUpperCase());

                    attendanceDAO.markAttendance(attendance);

                    // Send email notification if student is absent
                    if ("ABSENT".equalsIgnoreCase(attendanceStatus)) {
                        try {
                            emailUtil.sendAbsenceNotification(
                                    student.getEmail(),
                                    student.getName(),
                                    subject.getName(),
                                    date
                            );
                        } catch (Exception e) {
                            System.err.println("Error sending email to " + student.getEmail() + ": " + e.getMessage());
                        }
                    }
                }
            }

            session.setAttribute("message", "Attendance marked successfully!");
            session.setAttribute("messageType", "success");
            // Redirect to the same page to show the form again, possibly with success message
            response.sendRedirect(request.getContextPath() + "/teacher/mark-attendance");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error marking attendance: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}

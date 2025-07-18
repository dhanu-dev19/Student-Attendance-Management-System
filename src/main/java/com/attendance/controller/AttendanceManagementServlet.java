package com.attendance.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.attendance.dao.AttendanceDAO;
import com.attendance.dao.StudentDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.model.Attendance;
import com.attendance.model.Student;
import com.attendance.model.Subject;
import com.attendance.util.EmailUtil;

//@WebServlet("/attendance/*")
public class AttendanceManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AttendanceDAO attendanceDAO;
    private StudentDAO studentDAO;
    private SubjectDAO subjectDAO;
    private EmailUtil emailUtil;

    public void init() {
        attendanceDAO = new AttendanceDAO();
        studentDAO = new StudentDAO();
        subjectDAO = new SubjectDAO();
        emailUtil = new EmailUtil();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            switch (action) {
                case "/view":
                    viewAttendance(request, response);
                    break;
                case "/report":
                    generateReport(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            switch (action) {
                case "/mark":
                    markAttendance(request, response);
                    break;
                case "/update":
                    updateAttendance(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void markAttendance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        Date date = Date.valueOf(request.getParameter("date"));
        String[] studentIds = request.getParameterValues("studentIds");
        String[] statuses = request.getParameterValues("statuses");

        for (int i = 0; i < studentIds.length; i++) {
            int studentId = Integer.parseInt(studentIds[i]);
            String status = statuses[i];

            Attendance attendance = new Attendance();
            attendance.setStudentId(studentId);
            attendance.setSubjectId(subjectId);
            attendance.setDate(date);
            attendance.setStatus(status);

            attendanceDAO.markAttendance(attendance);

            // Send email notification if student is absent
            if ("Absent".equals(status)) {
                Student student = studentDAO.getStudentById(studentId);
                Subject subject = subjectDAO.getSubjectById(subjectId);
                EmailUtil.sendAbsenceNotification(student.getEmail(), student.getName(),
                        subject.getName(), date.toString());
            }
        }

        response.sendRedirect(request.getContextPath() + "/teacher/dashboard.jsp");
    }

    private void viewAttendance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("userType");

        if ("admin".equals(userType)) {
            // Admin can view all attendance records
            List<Attendance> attendanceList = attendanceDAO.getAllAttendance();
            request.setAttribute("attendanceList", attendanceList);
            request.getRequestDispatcher("/admin/view_attendance.jsp").forward(request, response);
        } else if ("teacher".equals(userType)) {
            // Teacher can view attendance for their subjects
            int teacherId = (int) session.getAttribute("userId");
            List<Attendance> attendanceList = attendanceDAO.getAttendanceBySubject(teacherId);
            request.setAttribute("attendanceList", attendanceList);
            request.getRequestDispatcher("/teacher/view_attendance.jsp").forward(request, response);
        } else if ("student".equals(userType)) {
            // Student can view their own attendance
            int studentId = (int) session.getAttribute("userId");
            List<Attendance> attendanceList = attendanceDAO.getAttendanceByStudent(studentId);
            request.setAttribute("attendanceList", attendanceList);
            request.getRequestDispatcher("/student/view_attendance.jsp").forward(request, response);
        }
    }

    private void updateAttendance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        String status = request.getParameter("status");

        Attendance attendance = attendanceDAO.getAttendanceById(attendanceId);
        attendance.setStatus(status);
        attendanceDAO.updateAttendance(attendance);

        // Send email notification if status changed to absent
        if ("Absent".equals(status)) {
            Student student = studentDAO.getStudentById(attendance.getStudentId());
            Subject subject = subjectDAO.getSubjectById(attendance.getSubjectId());
            emailUtil.sendAbsenceNotification(student.getEmail(), student.getName(),
                    subject.getName(), String.valueOf(attendance.getDate()));
        }

        response.sendRedirect(request.getContextPath() + "/attendance/view");
    }

    private void generateReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportType = request.getParameter("reportType");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String subjectId = request.getParameter("subjectId");
        String studentId = request.getParameter("studentId");

        List<Attendance> reportData = null;

        switch (reportType) {
            case "subject":
                reportData = attendanceDAO.getAttendanceBySubjectAndDateRange(
                        Integer.parseInt(subjectId),
                        Date.valueOf(startDate),
                        Date.valueOf(endDate));
                break;
            case "student":
                reportData = attendanceDAO.getAttendanceByStudentAndDateRange(
                        Integer.parseInt(studentId),
                        Date.valueOf(startDate),
                        Date.valueOf(endDate));
                break;
            case "date":
                reportData = attendanceDAO.getAttendanceByDateRange(
                        Date.valueOf(startDate),
                        Date.valueOf(endDate));
                break;
        }

        request.setAttribute("reportData", reportData);
        request.getRequestDispatcher("/admin/attendance_report.jsp").forward(request, response);
    }
}


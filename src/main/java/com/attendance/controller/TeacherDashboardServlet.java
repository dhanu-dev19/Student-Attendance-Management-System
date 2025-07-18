package com.attendance.controller;

import com.attendance.dao.AttendanceDAO;
import com.attendance.dao.StudentDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.dao.TeacherDAO;
import com.attendance.model.Attendance;
import com.attendance.model.Student;
import com.attendance.model.Subject;
import com.attendance.model.Teacher;
import com.google.gson.Gson;

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

//@WebServlet("/teacher/dashboard")
public class TeacherDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SubjectDAO subjectDAO;
    private StudentDAO studentDAO;
    private AttendanceDAO attendanceDAO;
    private TeacherDAO teacherDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        subjectDAO = new SubjectDAO();
        studentDAO = new StudentDAO();
        attendanceDAO = new AttendanceDAO();
        teacherDAO = new TeacherDAO();
        gson = new Gson();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher currentTeacher = (Teacher) session.getAttribute("currentTeacher");

        if (currentTeacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int teacherId = currentTeacher.getId();

        try {
            // Fetch dashboard statistics
            int totalSubjects = subjectDAO.getTotalSubjectsByTeacher(teacherId);
            int totalStudents = studentDAO.getTotalStudentsByTeacher(teacherId);
            double averageAttendance = attendanceDAO.getTeacherOverallAverageAttendance(teacherId);
            List<Attendance> lowAttendanceAlerts = attendanceDAO.getLowAttendanceAlertsForTeacher(teacherId, 75); // Threshold 75%
            long lowAttendanceCount = lowAttendanceAlerts.size();


            // Fetch subjects taught by the teacher with student and attendance stats
            List<Subject> subjects = subjectDAO.getSubjectsByTeacherId(teacherId);
            for (Subject subject : subjects) {
                subject.setTotalStudents(studentDAO.getTotalStudentsInSubject(subject.getId()));
                subject.setAverageAttendance(attendanceDAO.getSubjectAverageAttendance(subject.getId()));
            }

            // Fetch data for attendance overview chart
            Map<String, Double> attendanceOverview = attendanceDAO.getTeacherAttendanceOverview(teacherId);
            List<String> attendanceDates = attendanceOverview.keySet().stream().collect(Collectors.toList());
            List<Double> attendancePercentages = attendanceOverview.values().stream().collect(Collectors.toList());

            // Convert lists to JSON strings using Gson
            String jsonAttendanceDates = gson.toJson(attendanceDates);
            String jsonAttendancePercentages = gson.toJson(attendancePercentages);

            // Set attributes for JSP
            request.setAttribute("totalSubjects", totalSubjects);
            request.setAttribute("totalStudents", totalStudents);
            request.setAttribute("averageAttendance", String.format("%.2f", averageAttendance));
            request.setAttribute("lowAttendanceCount", lowAttendanceCount);
            request.setAttribute("lowAttendanceAlerts", lowAttendanceAlerts);
            request.setAttribute("subjects", subjects);
            request.setAttribute("attendanceDates", jsonAttendanceDates);
            request.setAttribute("attendancePercentages", jsonAttendancePercentages);


            request.getRequestDispatcher("/teacher/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading teacher dashboard: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}

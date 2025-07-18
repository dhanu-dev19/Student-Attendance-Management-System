package com.attendance.controller;

import com.attendance.dao.AttendanceDAO;
import com.attendance.dao.StudentDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.model.Subject;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


//@WebServlet("/admin/attendance/charts")
//@WebServlet("/admin/attendance/charts")
public class AttendanceChartServlet extends HttpServlet {
    private AttendanceDAO attendanceDAO;
    private SubjectDAO subjectDAO;
    private StudentDAO studentDAO;

    public void init() {
        attendanceDAO = new AttendanceDAO();
        subjectDAO = new SubjectDAO();
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectId = request.getParameter("subjectId");
        String className = request.getParameter("class");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // Overall trend
        Map<String, Double> overallTrend = attendanceDAO.getOverallAttendanceTrend(subjectId, className, startDate, endDate);

        // Subject-wise
        Map<String, Double> subjectWise = attendanceDAO.getSubjectWiseAttendance(className, startDate, endDate);

        // Class-wise
        Map<String, Double> classWise = attendanceDAO.getClassWiseAttendance(subjectId, startDate, endDate);

        // Distribution
        int[] distribution = attendanceDAO.getAttendanceDistribution(subjectId, className, startDate, endDate);

        // All subjects and classes for filters
        List<Subject> subjects = null;
        try {
            subjects = subjectDAO.getAllSubjects();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> classNames = studentDAO.getAllClassNames();

        Gson gson = new Gson();
        request.setAttribute("subjects", subjects);
        request.setAttribute("classes", classNames);

        request.setAttribute("overallTrendDates", gson.toJson(overallTrend.keySet()));
        request.setAttribute("overallTrendPercentages", gson.toJson(overallTrend.values()));
        request.setAttribute("subjectNames", gson.toJson(subjectWise.keySet()));
        request.setAttribute("subjectAttendancePercentages", gson.toJson(subjectWise.values()));
        request.setAttribute("classNames", gson.toJson(classWise.keySet()));
        request.setAttribute("classAttendancePercentages", gson.toJson(classWise.values()));
        request.setAttribute("attendanceDistribution", gson.toJson(distribution));

        request.getRequestDispatcher("/admin/attendance_charts.jsp").forward(request, response);
    }
}

package com.attendance.controller;

import com.attendance.dao.TeacherDAO;
import com.attendance.model.Teacher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TeacherLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TeacherDAO teacherDAO;

    @Override
    public void init() throws ServletException {
        teacherDAO = new TeacherDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Teacher teacher = teacherDAO.validateTeacher(email, password);

        if (teacher != null) {
            HttpSession session = request.getSession();
            session.setAttribute("teacher", teacher);
            session.setAttribute("user", teacher);
            session.setAttribute("currentTeacher", teacher);
            session.setAttribute("userType", "teacher");
            session.setAttribute("teacherId", teacher.getId());
            response.sendRedirect(request.getContextPath() + "/teacher/dashboard");
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}


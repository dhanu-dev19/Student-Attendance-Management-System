package com.attendance.controller;

import com.attendance.dao.TeacherDAO;
import com.attendance.model.Teacher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet("/admin/teachers")
public class TeacherManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TeacherDAO teacherDAO;

    @Override
    public void init() throws ServletException {
        teacherDAO = new TeacherDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "insert"; // Default action
        }

        try {
            switch (action) {
                case "insert":
                    insertTeacher(request, response);
                    break;
                case "update":
                    updateTeacher(request, response);
                    break;
                default:
                    listTeachers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert": // This case is typically handled by doPost, but added for completeness if form submits via GET
                    insertTeacher(request, response);
                    break;
                case "delete":
                    deleteTeacher(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update": // This case is typically handled by doPost, but added for completeness if form submits via GET
                    updateTeacher(request, response);
                    break;
                case "search":
                    searchTeachers(request, response);
                    break;
                case "view":
                    viewTeacherDetails(request, response);
                    break;
                default:
                    listTeachers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTeachers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Teacher> listTeacher = teacherDAO.getAllTeachers();
        request.setAttribute("teachers", listTeacher);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage_teachers.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/add_teacher.jsp"); // Assuming a separate JSP for adding
        dispatcher.forward(request, response);
    }

    private void insertTeacher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String subject = request.getParameter("subject");
        String phone = request.getParameter("phone");

        Teacher newTeacher = new Teacher(0, name, email, password, subject, phone, null); // createdAt will be set by DB
        teacherDAO.addTeacher(newTeacher);
        request.setAttribute("message", "Teacher added successfully!");
        request.setAttribute("messageType", "success");

        try {
            listTeachers(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Teacher existingTeacher = teacherDAO.getTeacherById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/edit_teacher.jsp"); // Assuming a separate JSP for editing
        request.setAttribute("teacher", existingTeacher);
        dispatcher.forward(request, response);
    }

    private void updateTeacher(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password"); // New password, might be empty
        String subject = request.getParameter("subject");
        String phone = request.getParameter("phone");

        Teacher existingTeacher = teacherDAO.getTeacherById(id);
        if (existingTeacher == null) {
            request.setAttribute("message", "Teacher not found for update.");
            request.setAttribute("messageType", "error");
            listTeachers(request, response);
            return;
        }

        // Create a teacher object with potentially updated fields
        Teacher teacherToUpdate = new Teacher(id, name, email,
                password.isEmpty() ? existingTeacher.getPassword() : password, // Use existing password if new one is empty
                subject, phone, existingTeacher.getCreatedAt());

        teacherDAO.updateTeacher(teacherToUpdate);
        request.setAttribute("message", "Teacher updated successfully!");
        request.setAttribute("messageType", "success");
        listTeachers(request, response);
    }

    private void deleteTeacher(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        teacherDAO.deleteTeacher(id);
        request.setAttribute("message", "Teacher deleted successfully!");
        request.setAttribute("messageType", "success");
        listTeachers(request, response);
    }

    private void searchTeachers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String query = request.getParameter("query");
        List<Teacher> searchResults = teacherDAO.searchTeachers(query); // Assuming a search method exists
        request.setAttribute("teachers", searchResults);
        request.setAttribute("message", "Search results for: '" + query + "'");
        request.setAttribute("messageType", "info");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage_teachers.jsp");
        dispatcher.forward(request, response);
    }

    private void viewTeacherDetails(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Teacher teacher = teacherDAO.getTeacherById(id);
        request.setAttribute("teacherDetails", teacher);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/teacher_details.jsp"); // Assuming a separate JSP for details
        dispatcher.forward(request, response);
    }
}
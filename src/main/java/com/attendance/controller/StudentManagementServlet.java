package com.attendance.controller;

import com.attendance.dao.StudentDAO;
import com.attendance.model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//@WebServlet("/admin/student")
public class StudentManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "insert"; // Default action
        }

        try {
            switch (action) {
                case "insert":
                    insertStudent(request, response);
                    break;
                case "update":
                    updateStudent(request, response);
                    break;
                default:
                    listStudent(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

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
                    insertStudent(request, response);
                    break;
                case "delete":
                    deleteStudent(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update": // This case is typically handled by doPost, but added for completeness if form submits via GET
                    updateStudent(request, response);
                    break;
                case "search":
                    searchStudents(request, response);
                    break;
                case "view":
                    viewStudentDetails(request, response);
                    break;
                default:
                    listStudent(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Student> listStudent = studentDAO.getAllStudents();
        request.setAttribute("students", listStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage_students.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage_students.jsp");
        dispatcher.forward(request, response);
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String className = request.getParameter("className");
        String rollNumber = request.getParameter("rollNumber");
        String branch = request.getParameter("branch");
        int year = Integer.parseInt(request.getParameter("year"));

        Student newStudent = new Student(0, name, email, password, className, rollNumber, branch, year);
        studentDAO.addStudent(newStudent);
        request.setAttribute("message", "Student added successfully!");
        request.setAttribute("messageType", "success");

        try {
            listStudent(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDAO.getStudentById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage_students.jsp");
        request.setAttribute("student", existingStudent);
        dispatcher.forward(request, response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password"); // New password, might be empty
        String className = request.getParameter("className");
        String rollNumber = request.getParameter("rollNumber");
        String branch = request.getParameter("branch");
        int year = Integer.parseInt(request.getParameter("year"));

        Student existingStudent = studentDAO.getStudentById(id);
        if (existingStudent == null) {
            request.setAttribute("message", "Student not found for update.");
            request.setAttribute("messageType", "error");
            listStudent(request, response);
            return;
        }

        // Create a student object with potentially updated fields
        Student studentToUpdate = new Student(id, name, email,
                password.isEmpty() ? existingStudent.getPassword() : password, // Use existing password if new one is empty
                className, rollNumber,branch,year);

        studentDAO.updateStudent(studentToUpdate);
        request.setAttribute("message", "Student updated successfully!");
        request.setAttribute("messageType", "success");
        listStudent(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        request.setAttribute("message", "Student deleted successfully!");
        request.setAttribute("messageType", "success");
        listStudent(request, response);
    }

    private void searchStudents(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String query = request.getParameter("query");
        List<Student> searchResults = studentDAO.searchStudents(query);
        request.setAttribute("students", searchResults);
        request.setAttribute("message", "Search results for: '" + query + "'");
        request.setAttribute("messageType", "info");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/manage_students.jsp");
        dispatcher.forward(request, response);
    }

    private void viewStudentDetails(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.getStudentById(id);
        request.setAttribute("studentDetails", student);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/student_details.jsp"); // Assuming a separate JSP for details
        dispatcher.forward(request, response);
    }
}

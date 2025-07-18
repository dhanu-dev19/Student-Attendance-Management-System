package com.attendance.controller;

import com.attendance.dao.SubjectDAO;
import com.attendance.model.Subject;
import com.attendance.dao.TeacherDAO;
import com.attendance.model.Teacher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//@WebServlet("/admin/subjects")
public class SubjectManagementServlet extends HttpServlet {
    private SubjectDAO subjectDAO;
    private TeacherDAO teacherDAO;

    public void init() {
        subjectDAO = new SubjectDAO();
        teacherDAO = new TeacherDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "add":
                    addSubject(request, response);
                    break;
                case "update":
                    updateSubject(request, response);
                    break;
                case "delete":
                    deleteSubject(request, response);
                    break;
                case "search":
                    searchSubject(request, response);
                    break;
                default:
                    listSubjects(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action != null && action.equals("edit")) {
                editSubjectForm(request, response);
            } else {
                listSubjects(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listSubjects(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Subject> listSubjects = subjectDAO.getAllSubjects();
        System.out.println("Subjects fetched for list: " + listSubjects.size() + " subjects");
        request.setAttribute("listSubjects", listSubjects);

        List<Teacher> teachers = teacherDAO.getAllTeachers();
        System.out.println("Teachers fetched: " + teachers.size() + " teachers");
        request.setAttribute("teachers", teachers);

        request.getRequestDispatcher("/admin/manage_subjects.jsp").forward(request, response);
    }

    private void addSubject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        Subject newSubject = new Subject();
        newSubject.setName(name);
        newSubject.setCode(code);
        newSubject.setTeacherId(teacherId);
        subjectDAO.addSubject(newSubject);
        response.sendRedirect("subjects");
    }

    private void updateSubject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        Subject subject = new Subject();
        subject.setId(id);
        subject.setName(name);
        subject.setCode(code);
        subject.setTeacherId(teacherId);
        subjectDAO.updateSubject(subject);
        response.sendRedirect("subjects");
    }

    private void deleteSubject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        subjectDAO.deleteSubject(id);
        response.sendRedirect("subjects");
    }

    private void searchSubject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String query = request.getParameter("query");
        List<Subject> searchResults = subjectDAO.searchSubjects(query);
        System.out.println("Subjects fetched for search: " + searchResults.size() + " subjects");
        request.setAttribute("listSubjects", searchResults);

        List<Teacher> teachers = teacherDAO.getAllTeachers();
        request.setAttribute("teachers", teachers);

        request.getRequestDispatcher("/admin/manage_subjects.jsp").forward(request, response);
    }

    private void editSubjectForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Subject existingSubject = subjectDAO.getSubjectById(id);
        request.setAttribute("subject", existingSubject);

        List<Teacher> teachers = teacherDAO.getAllTeachers();
        request.setAttribute("teachers", teachers);

        request.getRequestDispatcher("/admin/manage_subjects.jsp").forward(request, response);
    }
}

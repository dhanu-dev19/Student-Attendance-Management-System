package com.attendance.controller;

import com.attendance.dao.AdminDAO;
import com.attendance.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminLoginServlet extends HttpServlet
{
    private static final long serialVersionUTD = 1L;
    private AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        adminDAO = new AdminDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Admin admin = adminDAO.validateAdmin(email,password);

        if (admin != null)
        {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            session.setAttribute("user", admin); // ✅ Required by filter
            session.setAttribute("userType", "admin");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
        else
        {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
    }
}


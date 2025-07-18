package com.attendance.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.attendance.util.SmsUtil;


//@WebServlet("/admin/send-sms")
public class SendSmsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String teacherPhone = request.getParameter("teacherPhone");
        String studentName = request.getParameter("studentName");
        String subjectName = request.getParameter("subjectName");

        String message = "Student '" + studentName + "' has low attendance in subject '" + subjectName + "'.";

        try {
            SmsUtil.sendSms(teacherPhone, message);
            request.getSession().setAttribute("smsSuccess", "SMS sent to teacher successfully!");
        } catch (Exception e) {
            request.getSession().setAttribute("smsError", "Failed to send SMS: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}

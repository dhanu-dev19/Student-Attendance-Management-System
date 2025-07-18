package com.attendance.controller;

import com.attendance.util.EmailUtil;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SendEmailUtil
{
    private static final Properties emailProps = new Properties();
    private static String fromEmail;
    private static String password;

    static {
        try(InputStream input = SendEmailUtil.class.getClassLoader().getResourceAsStream("email.properties"))
        {
            if(input == null)
            {
                throw  new RuntimeException("Unable to find email.properties ");
            }
            emailProps.load(input);
            fromEmail = emailProps.getProperty("email.username");
            password = emailProps.getProperty("email.password");
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error loading email properties", e);
        }
    }

    public static void sendAttendanceNotification(String toEmail, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailProps.getProperty("email.smtp.host"));
        props.put("mail.smtp.port", emailProps.getProperty("email.smtp.port"));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }

    public static void sendLowAttendanceAlert(String toEmail, String studentName, String subjectName, double attendancePercentage) {
        String subject = "Low Attendance Alert";
        String message = String.format(
                "Dear %s,\n\n" +
                        "This is to inform you that your attendance in %s has fallen below 75%%.\n" +
                        "Current attendance: %.2f%%\n\n" +
                        "Please ensure regular attendance to maintain academic progress.\n\n" +
                        "Best regards,\n" +
                        "Attendance Management System",
                studentName, subjectName, attendancePercentage
        );

        sendAttendanceNotification(toEmail, subject, message);
    }

    public static void sendAttendanceReport(String toEmail, String studentName, String subjectName,
                                            int totalClasses, int presentClasses, double attendancePercentage) {
        String subject = "Attendance Report";
        String message = String.format(
                "Dear %s,\n\n" +
                        "Here is your attendance report for %s:\n\n" +
                        "Total Classes: %d\n" +
                        "Classes Attended: %d\n" +
                        "Attendance Percentage: %.2f%%\n\n" +
                        "Best regards,\n" +
                        "Attendance Management System",
                studentName, subjectName, totalClasses, presentClasses, attendancePercentage
        );

        sendAttendanceNotification(toEmail, subject, message);
    }

    public static void sendPasswordResetLink(String toEmail, String resetLink) {
        String subject = "Password Reset Request";
        String message = String.format(
                "Dear User,\n\n" +
                        "You have requested to reset your password. Please click on the following link to reset your password:\n\n" +
                        "%s\n\n" +
                        "If you did not request this password reset, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "Attendance Management System",
                resetLink
        );

        sendAttendanceNotification(toEmail, subject, message);
    }
}

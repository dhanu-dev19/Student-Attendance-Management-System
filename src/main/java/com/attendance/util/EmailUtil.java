package com.attendance.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class EmailUtil {
    private static Properties emailProps;

    static {
        try {
            emailProps = new Properties();
            InputStream input = EmailUtil.class.getClassLoader().getResourceAsStream("email.properties");
            if (input == null) {
                throw new RuntimeException("Unable to find email.properties");
            }
            emailProps.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading email properties", e);
        }
    }

    public static void sendAbsenceNotification(String toEmail, String studentName, String subjectName, String date) {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailProps.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", emailProps.getProperty("mail.smtp.port"));
        props.put("mail.smtp.auth", emailProps.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", emailProps.getProperty("mail.smtp.starttls.enable"));

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        emailProps.getProperty("mail.username"),
                        emailProps.getProperty("mail.password")
                );
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(
                    emailProps.getProperty("mail.from"),
                    emailProps.getProperty("mail.from.name")
            ));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Absence Notification - " + subjectName);

            String emailContent = String.format(
                    "Dear %s,\n\n" +
                            "This is to inform you that you were marked absent for %s on %s.\n\n" +
                            "Please ensure regular attendance for better academic performance.\n\n" +
                            "Best regards,\n" +
                            "Attendance Management System",
                    studentName, subjectName, date
            );

            message.setText(emailContent);
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending email", e);
        }
    }
}


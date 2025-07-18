package com.attendance.dao;

import com.attendance.model.Teacher;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    public Teacher validateTeacher(String email, String password) {
        String query = "SELECT * FROM teacher WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Teacher(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("subject"),
                            rs.getString("phone"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addTeacher(Teacher teacher) {
        String query = "INSERT INTO teacher (name, email, password, subject, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getEmail());
            pstmt.setString(3, teacher.getPassword());
            pstmt.setString(4, teacher.getSubject());
            pstmt.setString(5, teacher.getPhone());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTeacher(Teacher teacher) {
        String query = "UPDATE teacher SET name = ?, email = ?, password = ?, subject = ?, phone = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getEmail());
            pstmt.setString(3, teacher.getPassword());
            pstmt.setString(4, teacher.getSubject());
            pstmt.setString(5, teacher.getPhone());
            pstmt.setInt(6, teacher.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTeacher(int id) {
        String query = "DELETE FROM teacher WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teacher";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("subject"),
                        rs.getString("phone"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public Teacher getTeacherById(int id) {
        String query = "SELECT * FROM teacher WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Teacher(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("subject"),
                            rs.getString("phone"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Teacher getTeacherByEmail(String email) {
        String query = "SELECT * FROM teacher WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Teacher(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("subject"),
                            rs.getString("phone"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotalTeachers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM teacher";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public List<Teacher> searchTeachers(String query) {
        List<Teacher> teachers = new ArrayList<>();
        String searchQuery = "SELECT * FROM teacher WHERE name LIKE ? OR email LIKE ? OR subject LIKE ? OR phone LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(searchQuery)) {

            String param = "%" + query + "%";
            pstmt.setString(1, param);
            pstmt.setString(2, param);
            pstmt.setString(3, param);
            pstmt.setString(4, param);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    teachers.add(new Teacher(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("subject"),
                            rs.getString("phone"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }
}

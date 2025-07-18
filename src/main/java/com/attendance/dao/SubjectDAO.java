package com.attendance.dao;

import com.attendance.model.Subject;
import com.attendance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    public Subject getSubjectById(int id) throws SQLException {
        String sql = "SELECT * FROM subjects WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractSubjectFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects ORDER BY name";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                subjects.add(extractSubjectFromResultSet(rs));
            }
        }
        return subjects;
    }

    public List<Subject> getSubjectsByTeacherId(int teacherId) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects WHERE teacher_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    subjects.add(extractSubjectFromResultSet(rs));
                }
            }
        }
        return subjects;
    }

    public boolean addSubject(Subject subject) throws SQLException {
        String sql = "INSERT INTO subjects (name, code, teacher_id) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getCode());
            stmt.setInt(3, subject.getTeacherId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        subject.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean updateSubject(Subject subject) throws SQLException {
        String sql = "UPDATE subjects SET name = ?, code = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getCode());
            stmt.setInt(3, subject.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteSubject(int id) throws SQLException {
        String sql = "DELETE FROM subjects WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }





    public int getTotalSubjectsByTeacher(int teacherId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM subjects WHERE teacher_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    public List<Subject> searchSubjects(String query) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects WHERE name LIKE ? OR code LIKE ? ORDER BY name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    subjects.add(extractSubjectFromResultSet(rs));
                }
            }
        }
        return subjects;
    }

    public int getTotalSubjects() throws SQLException {
        String sql = "SELECT COUNT(*) FROM subjects";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public List<Subject> getSubjectsByStudentId(int studentId) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT DISTINCT s.*, t.name as teacher_name " +
                "FROM subjects s " +
                "JOIN attendance a ON s.id = a.subject_id " +
                "LEFT JOIN teacher t ON s.teacher_id = t.id " +
                "WHERE a.student_id = ? " +
                "ORDER BY s.name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setCode(rs.getString("code"));
                subject.setTeacherId(rs.getInt("teacher_id"));
                subject.setTeacherName(rs.getString("teacher_name"));
                subjects.add(subject);
            }
        }
        return subjects;
    }

    public int getTotalSubjectsByStudent(int studentId) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT subject_id) as total_subjects " +
                "FROM attendance WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_subjects");
            }
            return 0;
        }
    }

    private Subject extractSubjectFromResultSet(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setId(rs.getInt("id"));
        subject.setName(rs.getString("name"));
        subject.setCode(rs.getString("code"));
        subject.setTeacherId(rs.getInt("teacher_id"));
        return subject;
    }


}


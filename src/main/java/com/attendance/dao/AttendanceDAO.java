package com.attendance.dao;

import com.attendance.model.Attendance;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AttendanceDAO {

    public boolean markAttendance(Attendance attendance) {
        String query = "INSERT INTO attendance (student_id, subject_id, date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, attendance.getStudentId());
            pstmt.setInt(2, attendance.getSubjectId());
            pstmt.setDate(3, attendance.getDate());
            pstmt.setString(4, attendance.getStatus());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAttendance(Attendance attendance) {
        String query = "UPDATE attendance SET status = ? WHERE student_id = ? AND subject_id = ? AND date = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, attendance.getStatus());
            pstmt.setInt(2, attendance.getStudentId());
            pstmt.setInt(3, attendance.getSubjectId());
            pstmt.setDate(4, attendance.getDate());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE student_id = ? ORDER BY date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    attendances.add(new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getAttendanceBySubject(int subjectId) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE subject_id = ? ORDER BY date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, subjectId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    attendances.add(new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getAttendanceByDate(Date date) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE date = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDate(1, date);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    attendances.add(new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public Attendance getAttendance(int studentId, int subjectId, Date date) {
        String query = "SELECT * FROM attendance WHERE student_id = ? AND subject_id = ? AND date = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, subjectId);
            pstmt.setDate(3, date);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getAttendancePercentage(int studentId, int subjectId) {
        String query = "SELECT COUNT(*) as total, SUM(CASE WHEN status = 'PRESENT' THEN 1 ELSE 0 END) as present " +
                "FROM attendance WHERE student_id = ? AND subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, subjectId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    int present = rs.getInt("present");
                    return total > 0 ? (present * 100.0 / total) : 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Attendance> getAllAttendance() {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendance ORDER BY date DESC, id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    attendances.add(new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public Attendance getAttendanceById(int id) {
        String query = "SELECT * FROM attendance WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Attendance> getAttendanceBySubjectAndDateRange(int subjectId, Date startDate, Date endDate) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE subject_id = ? AND date BETWEEN ? AND ? ORDER BY date DESC, id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, subjectId);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    attendances.add(new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getAttendanceByStudentAndDateRange(int studentId, Date startDate, Date endDate) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE student_id = ? AND date BETWEEN ? AND ? ORDER BY date DESC, id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    attendances.add(new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public List<Attendance> getAttendanceByDateRange(Date startDate, Date endDate) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE date BETWEEN ? AND ? ORDER BY date DESC, id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    attendances.add(new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }
    public double getTeacherOverallAverageAttendance(int teacherId) throws SQLException {
        String sql = "SELECT COALESCE(AVG(CASE WHEN a.status = 'PRESENT' THEN 100.0 ELSE 0.0 END), 0) FROM attendance a " +
                "JOIN subjects s ON a.subject_id = s.id " +
                "WHERE s.teacher_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }

    public List<Attendance> getLowAttendanceAlertsForTeacher(int teacherId, double threshold) throws SQLException {
        List<Attendance> alerts = new ArrayList<>();
        String sql = "SELECT st.name as student_name, sub.name as subject_name, " +
                "(SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) as attendance_percentage, " +
                "st.id as student_id, sub.id as subject_id " +
                "FROM attendance a " +
                "JOIN student st ON a.student_id = st.id " +
                "JOIN subjects sub ON a.subject_id = sub.id " +
                "WHERE sub.teacher_id = ? " +
                "GROUP BY st.id, sub.id " +
                "HAVING attendance_percentage < ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, teacherId);
            pstmt.setDouble(2, threshold);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Attendance alert = new Attendance();
                    alert.setStudentName(rs.getString("student_name"));
                    alert.setSubjectName(rs.getString("subject_name"));
                    alert.setAttendancePercentage(rs.getDouble("attendance_percentage"));
                    alert.setStudentId(rs.getInt("student_id"));
                    alert.setSubjectId(rs.getInt("subject_id"));
                    alerts.add(alert);
                }
            }
        }
        return alerts;
    }

    public double getSubjectAverageAttendance(int subjectId) throws SQLException {
        String sql = "SELECT COALESCE(AVG(CASE WHEN status = 'PRESENT' THEN 100.0 ELSE 0.0 END), 0) FROM attendance WHERE subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subjectId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }

    public Map<String, Double> getTeacherAttendanceOverview(int teacherId) throws SQLException {
        Map<String, Double> attendanceOverview = new TreeMap<>();
        String sql = "SELECT DATE_FORMAT(a.date, '%Y-%m-%d') as attendance_date, " +
                "COALESCE(AVG(CASE WHEN a.status = 'PRESENT' THEN 100.0 ELSE 0.0 END), 0) as daily_average " +
                "FROM attendance a " +
                "JOIN subjects s ON a.subject_id = s.id " +
                "WHERE s.teacher_id = ? " +
                "GROUP BY a.date " +
                "ORDER BY a.date ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    attendanceOverview.put(rs.getString("attendance_date"), rs.getDouble("daily_average"));
                }
            }
        }
        return attendanceOverview;
    }

    public List<Attendance> getAttendanceBySubjectAndDate(int subjectId, Date date) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT a.*, s.name as student_name, sub.name as subject_name " +
                "FROM attendance a " +
                "JOIN student s ON a.student_id = s.id " +
                "JOIN subjects sub ON a.subject_id = sub.id " +
                "WHERE a.subject_id = ? AND a.date = ? " +
                "ORDER BY s.name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, subjectId);
            pstmt.setDate(2, date);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    );
                    attendance.setStudentName(rs.getString("student_name"));
                    attendance.setSubjectName(rs.getString("subject_name"));
                    attendances.add(attendance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public double getStudentOverallAverageAttendance(int studentId) throws SQLException {
        String sql = "SELECT AVG(CASE WHEN status = 'PRESENT' THEN 100 ELSE 0 END) as avg_attendance " +
                "FROM attendance WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_attendance");
            }
            return 0.0;
        }
    }

    public int getStudentPresentDays(int studentId) throws SQLException {
        String sql = "SELECT COUNT(*) as present_count FROM attendance " +
                "WHERE student_id = ? AND status = 'PRESENT'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("present_count");
            }
            return 0;
        }
    }

    public int getStudentAbsentDays(int studentId) throws SQLException {
        String sql = "SELECT COUNT(*) as absent_count FROM attendance " +
                "WHERE student_id = ? AND status = 'ABSENT'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("absent_count");
            }
            return 0;
        }
    }

    public double getSubjectAttendancePercentageForStudent(int subjectId, int studentId) throws SQLException {
        String sql = "SELECT " +
                "COUNT(CASE WHEN status = 'PRESENT' THEN 1 END) * 100.0 / COUNT(*) as attendance_percentage " +
                "FROM attendance " +
                "WHERE subject_id = ? AND student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subjectId);
            stmt.setInt(2, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("attendance_percentage");
            }
            return 0.0;
        }
    }

    public Map<String, Double> getStudentAttendanceOverview(int studentId) throws SQLException {
        Map<String, Double> overview = new LinkedHashMap<>();
        String sql = "SELECT DATE(date) as attendance_date, " +
                "COUNT(CASE WHEN status = 'PRESENT' THEN 1 END) * 100.0 / COUNT(*) as attendance_percentage " +
                "FROM attendance " +
                "WHERE student_id = ? " +
                "GROUP BY DATE(date) " +
                "ORDER BY DATE(date) DESC " +
                "LIMIT 30";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                overview.put(rs.getDate("attendance_date").toString(),
                        rs.getDouble("attendance_percentage"));
            }
        }
        return overview;
    }

    public List<Attendance> getRecentAttendanceByStudent(int studentId, int limit) throws SQLException {
        List<Attendance> records = new ArrayList<>();
        String sql = "SELECT a.*, s.name as subject_name " +
                "FROM attendance a " +
                "JOIN subjects s ON a.subject_id = s.id " +
                "WHERE a.student_id = ? " +
                "ORDER BY a.date DESC, a.created_at DESC " +
                "LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setId(rs.getInt("id"));
                attendance.setStudentId(rs.getInt("student_id"));
                attendance.setSubjectId(rs.getInt("subject_id"));
                attendance.setDate(rs.getDate("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setSubjectName(rs.getString("subject_name"));
                records.add(attendance);
            }
        }
        return records;
    }

    // Get attendance records for a student with optional filters
    public List<Attendance> getAttendanceByStudentWithFilters(int studentId, Integer subjectId, Date startDate, Date endDate) throws SQLException {
        List<Attendance> records = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT a.*, s.name as subject_name FROM attendance a JOIN subjects s ON a.subject_id = s.id WHERE a.student_id = ?");
        if (subjectId != null) sql.append(" AND a.subject_id = ").append(subjectId);
        if (startDate != null) sql.append(" AND a.date >= '").append(startDate).append("'");
        if (endDate != null) sql.append(" AND a.date <= '").append(endDate).append("'");
        sql.append(" ORDER BY a.date DESC, a.created_at DESC");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setId(rs.getInt("id"));
                attendance.setStudentId(rs.getInt("student_id"));
                attendance.setSubjectId(rs.getInt("subject_id"));
                attendance.setDate(rs.getDate("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setSubjectName(rs.getString("subject_name"));
                records.add(attendance);
            }
        }
        return records;
    }

    // Get date-wise attendance percentage for a student (optionally filtered)
    public Map<String, Double> getStudentAttendanceOverview(int studentId, Integer subjectId, Date startDate, Date endDate) throws SQLException {
        Map<String, Double> overview = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder("SELECT DATE(date) as attendance_date, COUNT(CASE WHEN status = 'PRESENT' THEN 1 END) * 100.0 / COUNT(*) as attendance_percentage FROM attendance WHERE student_id = ?");
        if (subjectId != null) sql.append(" AND subject_id = ").append(subjectId);
        if (startDate != null) sql.append(" AND date >= '").append(startDate).append("'");
        if (endDate != null) sql.append(" AND date <= '").append(endDate).append("'");
        sql.append(" GROUP BY DATE(date) ORDER BY DATE(date) DESC LIMIT 30");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                overview.put(rs.getDate("attendance_date").toString(), rs.getDouble("attendance_percentage"));
            }
        }
        return overview;
    }

    // Get subject-wise summary for a student
    public List<Map<String, Object>> getStudentSubjectSummary(int studentId) throws SQLException {
        List<Map<String, Object>> summary = new ArrayList<>();
        String sql = "SELECT s.name as subject_name, COUNT(*) as total_classes, " +
                "SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) as present_count, " +
                "SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END) as absent_count, " +
                "(SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) as attendance_percentage " +
                "FROM attendance a JOIN subjects s ON a.subject_id = s.id WHERE a.student_id = ? GROUP BY a.subject_id, s.name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("subjectName", rs.getString("subject_name"));
                row.put("totalClasses", rs.getInt("total_classes"));
                row.put("presentCount", rs.getInt("present_count"));
                row.put("absentCount", rs.getInt("absent_count"));
                row.put("attendancePercentage", rs.getDouble("attendance_percentage"));
                summary.add(row);
            }
        }
        return summary;
    }


    public List<Attendance> getAttendanceByStudentAndSubject(int studentId, int subjectId) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT a.*, s.name as student_name, sub.name as subject_name " +
                "FROM attendance a " +
                "JOIN student s ON a.student_id = s.id " +
                "JOIN subjects sub ON a.subject_id = sub.id " +
                "WHERE a.student_id = ? AND a.subject_id = ? " +
                "ORDER BY a.date DESC, a.id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, subjectId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance(
                            rs.getInt("id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDate("date"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at")
                    );
                    attendance.setStudentName(rs.getString("student_name"));
                    attendance.setSubjectName(rs.getString("subject_name"));
                    attendances.add(attendance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public Map<String, Double> getDailyAttendanceOverview() throws SQLException {
        Map<String, Double> overview = new LinkedHashMap<>();
        String sql = "SELECT date, " +
                "SUM(CASE WHEN status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) as percentage " +
                "FROM attendance GROUP BY date ORDER BY date ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                overview.put(rs.getDate("date").toString(), rs.getDouble("percentage"));
            }
        }
        return overview;
    }

    public List<Map<String, Object>> getRecentActivities(int limit) throws SQLException {
        List<Map<String, Object>> activities = new ArrayList<>();
        String sql = "SELECT a.date, s.name as student_name, sub.name as subject_name, t.name as teacher_name, a.status " +
                "FROM attendance a " +
                "JOIN student s ON a.student_id = s.id " +
                "JOIN subjects sub ON a.subject_id = sub.id " +
                "JOIN teacher t ON sub.teacher_id = t.id " +
                "ORDER BY a.created_at DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> activity = new HashMap<>();
                activity.put("date", rs.getDate("date"));
                activity.put("studentName", rs.getString("student_name"));
                activity.put("subjectName", rs.getString("subject_name"));
                activity.put("teacherName", rs.getString("teacher_name"));
                activity.put("status", rs.getString("status"));
                activities.add(activity);
            }
        }
        return activities;
    }

    public List<Map<String, Object>> getLowAttendanceAlerts(double threshold) throws SQLException {
        List<Map<String, Object>> alerts = new ArrayList<>();
        String sql = "SELECT s.id as student_id, s.name as student_name, sub.id as subject_id, sub.name as subject_name, " +
                "t.name as teacher_name, t.phone as teacher_phone, " +
                "SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) as attendance_percentage " +
                "FROM attendance a " +
                "JOIN student s ON a.student_id = s.id " +
                "JOIN subjects sub ON a.subject_id = sub.id " +
                "JOIN teacher t ON sub.teacher_id = t.id " +
                "GROUP BY s.id, sub.id " +
                "HAVING attendance_percentage < ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, threshold);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> alert = new HashMap<>();
                alert.put("studentId", rs.getInt("student_id"));
                alert.put("studentName", rs.getString("student_name"));
                alert.put("subjectId", rs.getInt("subject_id"));
                alert.put("subjectName", rs.getString("subject_name"));
                alert.put("teacherName", rs.getString("teacher_name"));
                alert.put("teacherPhone", rs.getString("teacher_phone"));
                alert.put("attendancePercentage", rs.getDouble("attendance_percentage"));
                alerts.add(alert);
            }
        }
        return alerts;
    }


    // In AttendanceDAO.java
    public Map<String, Double> getOverallAttendanceTrend(String subjectId, String className, String startDate, String endDate) {
        Map<String, Double> trend = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT date, SUM(CASE WHEN status='PRESENT' THEN 1 ELSE 0 END)*100.0/COUNT(*) as percentage " +
                        "FROM attendance a JOIN student s ON a.student_id = s.id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (subjectId != null && !subjectId.isEmpty()) {
            sql.append(" AND a.subject_id = ?");
            params.add(Integer.parseInt(subjectId));
        }
        if (className != null && !className.isEmpty()) {
            sql.append(" AND s.class_name = ?");
            params.add(className);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND a.date >= ?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND a.date <= ?");
            params.add(Date.valueOf(endDate));
        }
        sql.append(" GROUP BY date ORDER BY date ASC");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trend.put(rs.getDate("date").toString(), rs.getDouble("percentage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trend;
    }

    public Map<String, Double> getSubjectWiseAttendance(String className, String startDate, String endDate) {
        Map<String, Double> map = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT sub.name, SUM(CASE WHEN a.status='PRESENT' THEN 1 ELSE 0 END)*100.0/COUNT(*) as percentage " +
                        "FROM attendance a JOIN subjects sub ON a.subject_id = sub.id JOIN student s ON a.student_id = s.id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (className != null && !className.isEmpty()) {
            sql.append(" AND s.class_name = ?");
            params.add(className);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND a.date >= ?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND a.date <= ?");
            params.add(Date.valueOf(endDate));
        }
        sql.append(" GROUP BY sub.id, sub.name ORDER BY sub.name");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("name"), rs.getDouble("percentage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, Double> getClassWiseAttendance(String subjectId, String startDate, String endDate) {
        Map<String, Double> map = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT s.class_name, SUM(CASE WHEN a.status='PRESENT' THEN 1 ELSE 0 END)*100.0/COUNT(*) as percentage " +
                        "FROM attendance a JOIN student s ON a.student_id = s.id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (subjectId != null && !subjectId.isEmpty()) {
            sql.append(" AND a.subject_id = ?");
            params.add(Integer.parseInt(subjectId));
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND a.date >= ?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND a.date <= ?");
            params.add(Date.valueOf(endDate));
        }
        sql.append(" GROUP BY s.class_name ORDER BY s.class_name");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("class_name"), rs.getDouble("percentage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public int[] getAttendanceDistribution(String subjectId, String className, String startDate, String endDate) {
        int[] counts = new int[2]; // [present, absent]
        StringBuilder sql = new StringBuilder(
                "SELECT status, COUNT(*) as cnt FROM attendance a JOIN student s ON a.student_id = s.id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (subjectId != null && !subjectId.isEmpty()) {
            sql.append(" AND a.subject_id = ?");
            params.add(Integer.parseInt(subjectId));
        }
        if (className != null && !className.isEmpty()) {
            sql.append(" AND s.class_name = ?");
            params.add(className);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND a.date >= ?");
            params.add(Date.valueOf(startDate));
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND a.date <= ?");
            params.add(Date.valueOf(endDate));
        }
        sql.append(" GROUP BY status");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status");
                int cnt = rs.getInt("cnt");
                if ("PRESENT".equalsIgnoreCase(status)) counts[0] = cnt;
                else if ("ABSENT".equalsIgnoreCase(status)) counts[1] = cnt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counts;
    }

    public double getOverallAverageAttendance() {
        String sql = "SELECT (SUM(CASE WHEN status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) as avg_attendance FROM attendance";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("avg_attendance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<Map<String, Object>> getAttendanceRecords(String subjectId, String className, String branch, String year, String date) {
        List<Map<String, Object>> records = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT a.id, a.date, a.status, s.name as studentName, s.class_name as className, s.branch as branch, s.year as year, sub.name as subjectName " +
                        "FROM attendance a " +
                        "JOIN student s ON a.student_id = s.id " +
                        "JOIN subjects sub ON a.subject_id = sub.id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (subjectId != null && !subjectId.isEmpty()) {
            sql.append(" AND a.subject_id = ?");
            params.add(Integer.parseInt(subjectId));
        }
        if (className != null && !className.isEmpty()) {
            sql.append(" AND s.class_name = ?");
            params.add(className);
        }
        if (branch != null && !branch.isEmpty()) {
            sql.append(" AND s.branch = ?");
            params.add(branch);
        }
        if (year != null && !year.isEmpty()) {
            sql.append(" AND s.year = ?");
            params.add(Integer.parseInt(year));
        }
        if (date != null && !date.isEmpty()) {
            sql.append(" AND a.date = ?");
            params.add(Date.valueOf(date));
        }
        sql.append(" ORDER BY a.date DESC, a.id DESC");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                record.put("id", rs.getInt("id"));
                record.put("date", rs.getDate("date").toString());
                record.put("status", rs.getString("status"));
                record.put("studentName", rs.getString("studentName"));
                record.put("className", rs.getString("className"));
                record.put("branch", rs.getString("branch"));
                record.put("year", rs.getInt("year"));
                record.put("subjectName", rs.getString("subjectName"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public Map<String, Double> getAttendanceOverview(String subjectId, String className, String branch, String year) {
        Map<String, Double> overview = new LinkedHashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT a.date, SUM(CASE WHEN a.status='PRESENT' THEN 1 ELSE 0 END)*100.0/COUNT(*) as percentage " +
                        "FROM attendance a JOIN student s ON a.student_id = s.id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (subjectId != null && !subjectId.isEmpty()) {
            sql.append(" AND a.subject_id = ?");
            params.add(Integer.parseInt(subjectId));
        }
        if (className != null && !className.isEmpty()) {
            sql.append(" AND s.class_name = ?");
            params.add(className);
        }
        if (branch != null && !branch.isEmpty()) {
            sql.append(" AND s.branch = ?");
            params.add(branch);
        }
        if (year != null && !year.isEmpty()) {
            sql.append(" AND s.year = ?");
            params.add(Integer.parseInt(year));
        }
        sql.append(" GROUP BY a.date ORDER BY a.date ASC");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                overview.put(rs.getDate("date").toString(), rs.getDouble("percentage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overview;
    }
}


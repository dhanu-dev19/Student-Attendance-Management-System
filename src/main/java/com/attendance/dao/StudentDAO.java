package com.attendance.dao;

import com.attendance.model.Student;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public Student validateStudent(String email, String password) {
        String query = "SELECT * FROM student WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("class_name"),
                            rs.getString("password"),
                            rs.getString("roll_number"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO student (name, email, class_name, roll_number, branch, year, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getClassName());
            pstmt.setString(4, student.getRollNumber());
            pstmt.setString(5, student.getBranch());
            pstmt.setInt(6, student.getYear());
            pstmt.setString(7, student.getPassword());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, email = ?, class_name = ?, roll_number = ?, branch = ?, year = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getClassName());
            pstmt.setString(4, student.getRollNumber());
            pstmt.setString(5, student.getBranch());
            pstmt.setInt(6, student.getYear());
            pstmt.setInt(7, student.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean deleteStudent(int id) {
        String query = "DELETE FROM student WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("class_name"),
                        rs.getString("roll_number"),
                        rs.getString("branch"),
                        rs.getInt("year")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> getStudentsByClass(String className) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student WHERE class_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, className);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("class_name"),
                            rs.getString("password"),
                            rs.getString("roll_number"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student getStudentById(int id) {
        String query = "SELECT * FROM student WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("class_name"),
                            rs.getString("password"),
                            rs.getString("roll_number"),
                            rs.getString("branch"),
                            rs.getInt("year"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student getStudentByEmail(String email) {
        String query = "SELECT * FROM student WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("class_name"),
                            rs.getString("password"),
                            rs.getString("roll_number"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotalStudents() throws SQLException {
        String sql = "SELECT COUNT(*) FROM student";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public List<Student> searchStudents(String searchTerm) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student WHERE name LIKE ? OR email LIKE ? OR roll_number LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("class_name"),
                            rs.getString("password"),
                            rs.getString("roll_number"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<String> getUniqueBranches() {
        List<String> branches = new ArrayList<>();
        String query = "SELECT DISTINCT branch FROM student ORDER BY branch";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                branches.add(rs.getString("branch"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches;
    }

    public List<Integer> getUniqueYears() {
        List<Integer> years = new ArrayList<>();
        String query = "SELECT DISTINCT year FROM student ORDER BY year";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }


    public List<Student> getStudentsByBranchAndYear(String branch, int year) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student WHERE branch = ? AND year = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, branch);
            pstmt.setInt(2, year);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setClassName(rs.getString("class_name"));
                    student.setRollNumber(rs.getString("roll_number"));
                    student.setBranch(rs.getString("branch"));
                    student.setYear(rs.getInt("year"));
                    // Set other fields if necessary
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    public List<Student> getStudentsBySubjectId(int subjectId) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT DISTINCT s.* FROM student s JOIN attendance a ON s.id = a.student_id WHERE a.subject_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, subjectId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("class_name"),
                            rs.getString("password"),
                            rs.getString("roll_number"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Student> getStudentsBySubjectAndBranchAndYear(int subjectId, String branch, int year) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT DISTINCT s.* FROM student s JOIN attendance a ON s.id = a.student_id WHERE a.subject_id = ? AND s.branch = ? AND s.year = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, subjectId);
            pstmt.setString(2, branch);
            pstmt.setInt(3, year);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("class_name"),
                            rs.getString("password"),
                            rs.getString("roll_number"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public int getTotalStudentsByTeacher(int teacherId) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT a.student_id) FROM attendance a JOIN subjects sub ON a.subject_id = sub.id WHERE sub.teacher_id = ?";
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

    public int getTotalStudentsInSubject(int subjectId) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT student_id) FROM attendance WHERE subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subjectId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public List<String> getAllClassNames() {
        List<String> classNames = new ArrayList<>();
        String sql = "SELECT DISTINCT class_name FROM student ORDER BY class_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                classNames.add(rs.getString("class_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classNames;
    }

    public List<String> getAllBranches() {
        List<String> branches = new ArrayList<>();
        String sql = "SELECT DISTINCT branch FROM student ORDER BY branch";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                branches.add(rs.getString("branch"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches;
    }

    public List<Integer> getAllYears() {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT year FROM student ORDER BY year";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return years;
    }
}


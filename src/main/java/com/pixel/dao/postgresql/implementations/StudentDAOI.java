package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.StudentDAO;
import com.pixel.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOI implements StudentDAO {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public StudentDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    @Override
    public List<Student> getListFull() throws SQLException {
        this.rs = getRSAllStudents();
        return getListFromRS(rs);
    }

    public List<Student> getListByValue(String valeName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valeName, value));
    }

    public int getExperience(Student s) throws SQLException {
        int id = s.getId();
        String query = "select exp_gained from quests_completed WHERE (user_id = ?);";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        int total_exp = 0;
        while (rs.next()) {
            total_exp += rs.getInt("exp_gained");
        }
        return total_exp;
    }

    @Override
    public boolean save(Student s) throws SQLException {
        String query = "" +
                "WITH ins1 AS (" +
                "    INSERT INTO users(name, password, role_name)" +
                "        VALUES (?, ?, ?)" +
                "        RETURNING id AS user_id" +
                ") " +
                "INSERT INTO students (user_id, mentor_id, class_id)" +
                "SELECT user_id, ?, ? FROM ins1;";
        this.ps = c.prepareStatement(query);
        ps.setString(1, s.getName());
        ps.setString(2, s.getPassword());
        ps.setString(3, s.getRoleName());
        ps.setInt(4, s.getMentor_id());
        ps.setInt(5, s.getCass_id());
        return ps.execute();
    }

    @Override
    public boolean update(Student s) throws SQLException {
        int id = s.getId();

        String query = "UPDATE users SET name = ?, password = ?, role_name = ? WHERE id = "+id+"";
        this.ps = c.prepareStatement(query);
        ps.setString(1, s.getName());
        ps.setString(2, s.getPassword());
        ps.setString(3, s.getRoleName());
        ps.executeUpdate();

        query = "UPDATE students SET mentor_id = ?, class_id = ? WHERE user_id = "+id+"";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, s.getMentor_id());
        ps.setInt(2, s.getCass_id());
        ps.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(Student s) throws SQLException {
        this.stmt = c.createStatement();
        String query = "DELETE FROM students WHERE user_id = " + s.getId() + "; DELETE FROM users WHERE id = " + s.getId() +"";
        return stmt.execute(query);
    }

    @Override
    public Student getById(int id) throws SQLException {
        String query = "select id, name,password,role_name,mentor_id,class_id from users " +
                "left join students s on users.id = s.user_id WHERE (id = s.user_id AND (id = ?));";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        String query = "select id, name,password,role_name,mentor_id,class_id from users " +
                "left join students s on users.id = s.user_id WHERE (id = s.user_id AND ("+valueName+" = ?));";
        this.ps = c.prepareStatement(query);
//        ps.setString(1);
        ps.setString(1, value);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    private ResultSet getRSAllStudents() throws SQLException {
        String query = "select id, name,password,role_name,mentor_id,class_id from users " +
                "left join students s on users.id = s.user_id WHERE id = s.user_id ;";
        Statement stmt = c.createStatement();
        return stmt.executeQuery(query);
    }

    private List<Student> getListFromRS(ResultSet rs) throws SQLException {
        List<Student> list = new ArrayList<>();
        while (rs.next()) list.add(extractStudentFromRS(rs));
        c.close();
        return list;

    }

    private Student extractStudentFromRS(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("role_name"),
                rs.getInt("mentor_id"),
                rs.getInt("class_id"));
    }


    private void close() throws SQLException {
        ps.close();
        stmt.close();
        c.close();
    }

}

package com.pixel.dao.postgresql;

import com.pixel.model.Student;
import com.pixel.dao.postgresql.interfaces.UsersDAOInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements UsersDAOInterface {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public StudentDAO() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    @Override
    public List<Student> getFullList() throws SQLException {
        this.rs = getAllStudentsRS();
        return getListFromRS(rs);
    }

    public List<Student> getByValue(String valeName, String value) throws SQLException {
        return getListFromRS(getByValueRS(valeName, value));
    }

    @Override
    public boolean save(Object o) {
        return false;
    }

    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }

    private ResultSet getByValueRS(String valueName, String value) throws SQLException {
        String query = "select id, name,password,role_name,mentor_id,class_id from users " +
                "left join students s on users.id = s.user_id WHERE (id = s.user_id AND ("+valueName+" = ?));";
        this.ps = c.prepareStatement(query);
//        ps.setString(1);
        ps.setString(1, value);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    private ResultSet getAllStudentsRS() throws SQLException {
        String query = "select id, name,password,role_name,mentor_id,class_id from users " +
                "left join students s on users.id = s.user_id WHERE id = s.user_id ;";
        Statement stmt = c.createStatement();
        return stmt.executeQuery(query);
    }

    private List<Student> getListFromRS(ResultSet rs) throws SQLException {
        List<Student> list = new ArrayList<>();
        while (rs.next()) list.add(extractStudentFromRS(rs));
        rs.close();
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

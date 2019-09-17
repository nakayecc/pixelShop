package com.pixel.dao.postgresql.implementations;

import com.pixel.model.Student;
import com.pixel.model.StudentsClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassesDAOI {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public StudentsClass getClassById(int id) throws SQLException {
        String query = "select id, name from classes WHERE (id = ?);";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private List<StudentsClass> getListFromRS(ResultSet rs) throws SQLException {
        List<StudentsClass> list = new ArrayList<>();
        while (rs.next()) list.add(extractStudentFromRS(rs));
        return list;}

    private StudentsClass extractStudentFromRS(ResultSet rs) throws SQLException {
        return new StudentsClass(
                rs.getInt("id"),
                rs.getString("name"));
    }

}

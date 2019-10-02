package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.interfaces.ClassesDAO;
import com.pixel.model.StudentsClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassesDAOI implements ClassesDAO {
    private Connection connection;

    public ClassesDAOI(Connection connection) {
        this.connection = connection;
    }

    public boolean createClass(String name) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO classes(name)  VALUES (?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        return preparedStatement.execute();

    }

    public StudentsClass getClassById(int id) throws SQLException {
        String query = "select id, name from classes WHERE (id = ?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return getListFromRS(rs).get(0);
    }

    @Override
    public List<StudentsClass> getFullList() throws SQLException {
        ResultSet resultSet;
        resultSet = getRSAllStudentClass();
        return getListFromRS(resultSet);
    }

    private List<StudentsClass> getListFromRS(ResultSet rs) throws SQLException {
        List<StudentsClass> list = new ArrayList<>();
        while (rs.next()) list.add(extractStudentsClassFromRS(rs));
        return list;
    }

    private StudentsClass extractStudentsClassFromRS(ResultSet rs) throws SQLException {
        return new StudentsClass(
                rs.getInt("id"),
                rs.getString("name"));
    }
    public void connClose() throws SQLException {
        connection.close();

    }

    private ResultSet getRSAllStudentClass() throws SQLException {
        String query = "select id, name from classes ";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

}

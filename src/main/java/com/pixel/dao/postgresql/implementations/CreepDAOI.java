package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.interfaces.CreepDAO;
import com.pixel.model.Creep;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreepDAOI extends UserDAOI implements CreepDAO {
    private Connection connection;

    public CreepDAOI(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    public List<Creep> getListFull() throws SQLException {
        ResultSet resultSet;
        resultSet = getRSAllCreeps();
        return getListFromRS(resultSet);
    }

    public List<Creep> getListByValue(String valeName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valeName, value));
    }

    @Override
    public boolean save(Creep creep) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "" +
                "WITH ins1 AS (" +
                "    INSERT INTO users(name, password, role_name)" +
                "        VALUES (?, ?, ?)" +
                "        RETURNING id AS user_id" +
                ") " +
                "INSERT INTO creeps (user_id)" +
                "SELECT user_id, ? FROM ins1;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, creep.getName());
        preparedStatement.setString(2, creep.getPassword());
        preparedStatement.setString(3, creep.getRoleName());
        return preparedStatement.execute();
    }

    @Override
    public boolean update(Creep creep) throws SQLException {
        updateInUserTable(creep.getId(), creep.getName(), creep.getPassword(), creep.getRoleName());
        return true;
    }

    @Override
    public boolean delete(Creep creep) throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        String query = "DELETE FROM mentors WHERE user_id = " + creep.getId() + "; DELETE FROM users WHERE id = " + creep.getId() + "";
        return statement.execute(query);
    }

    @Override
    public Creep getById(int id) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name,password,role_name, class_id from users " +
                "left join mentors s on users.id = s.user_id WHERE (id = s.user_id AND (id = ?));";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "select id, name,password,role_name from users " +
                "left join creeps s on users.id = s.user_id WHERE (id = s.user_id AND (" + valueName + " = ?));";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    private ResultSet getRSAllCreeps() throws SQLException {
        String query = "select id, name,password,role_name from users " +
                "left join creeps s on users.id = s.user_id WHERE id = s.user_id ;";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private List<Creep> getListFromRS(ResultSet rs) throws SQLException {
        List<Creep> list = new ArrayList<>();
        while (rs.next()) list.add(extractCreepsFromRS(rs));
        return list;

    }

    private Creep extractCreepsFromRS(ResultSet rs) throws SQLException {
        return new Creep(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("role_name"));
    }
    public void connClose() throws SQLException {
        connection.close();

    }


}

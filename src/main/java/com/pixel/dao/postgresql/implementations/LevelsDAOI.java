package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.model.Level;

import java.sql.*;
import java.util.HashMap;

public class LevelsDAOI {
    private Connection connection;

    public LevelsDAOI(Connection connection) {
        this.connection = connection;
    }

    public HashMap<String, Integer> getLevelMap() throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select * from levels;";
        preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        HashMap<String, Integer> levels = new HashMap<String, Integer>();
        while (rs.next()) {
            String levelName = rs.getString("level_name");
            int expRequired = rs.getInt("exp_req");
            levels.put(levelName, expRequired);
        }
        return levels;

    }

    public int getLevelIdByName(String name) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "SELECT id FROM levels WHERE level_name = ? ;";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            return rs.getInt("id");
        }
        return 0;


    }

    public void connClose() throws SQLException {
        connection.close();

    }

    public boolean addLevel(Level level) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO levels(level_name, exp_req)  VALUES (?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, level.getLevelName());
        preparedStatement.setInt(2, level.getExpReq());
        return preparedStatement.executeUpdate() == 1;
    }

    public boolean deleteLevelById(int id) throws SQLException {
        Statement statement;
        String query = "DELETE FROM levels WHERE id = " + id + "";
        statement = connection.createStatement();
        return statement.execute(query);
    }
}

package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;

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
    public void connClose() throws SQLException {
        connection.close();

    }
}

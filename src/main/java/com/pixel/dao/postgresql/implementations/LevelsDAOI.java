package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;

import java.sql.*;
import java.util.HashMap;

public class LevelsDAOI {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public LevelsDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    public HashMap<String, Integer> getLevelMap() throws SQLException {
        String query = "select * from levels;";
        this.ps = c.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        HashMap<String, Integer> levels = new HashMap<String, Integer>();
        while (rs.next()) {
            String levelName = rs.getString("level_name");
            int expRequired = rs.getInt("exp_req");
            levels.put(levelName, expRequired);
        }
        return levels;

    }
}

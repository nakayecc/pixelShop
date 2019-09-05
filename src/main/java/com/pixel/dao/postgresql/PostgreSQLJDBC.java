package com.pixel.dao.postgresql;

import java.sql.*;

public class PostgreSQLJDBC {
    private Connection c;
    private final String url;
    private String database;
    private String password;

    public PostgreSQLJDBC() {

        this.url = "jdbc:postgresql://nabielec.synology.me:5432/codecool?currentSchema=quest_store";
        this.database = "codecooler";
        this.password = "codecool";
        this.c = getConnection();
    }

    public Connection getConnection() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(this.url, this.database, this.password); // set user and password
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }



//    public void execute(String query) throws SQLException {
//        PreparedStatement ps = c.prepareStatement(query);
//        ps.executeUpdate();
//        c.close();
//    }

}

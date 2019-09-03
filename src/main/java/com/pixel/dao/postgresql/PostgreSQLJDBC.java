package com.pixel.dao.postgresql;

import java.sql.*;

public class PostgreSQLJDBC {
    private Connection c;
    private Statement stmt = null;
    private ResultSet rs = null;
    private final String url;
    private String database;
    private String password;

    public PostgreSQLJDBC() {
        this.url = "jdbc:postgresql://nabielec.synology.me:5432/postgres";
        this.database = "codecooler";
        this.password = "codecool";
        this.c = getConnection();
    }

    private Connection getConnection() {
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

    public ResultSet executeQuery(String query){
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }



    public void execute(String query){
        try {
            stmt = c.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            c.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

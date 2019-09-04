package com.pixel.dao.postgresql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
    private String tableName;

    public UsersDAO(String tableName) {
        this.tableName = tableName;
    }

    public ResultSet getFullList() throws SQLException {
        PostgreSQLJDBC ds = new PostgreSQLJDBC();
        String query = "SELECT * FROM "+ tableName +"";
        return ds.executeQuery(query);
    }

    public String checkUserRole(int id){
        PostgreSQLJDBC ds = new PostgreSQLJDBC();

        String query_creep = "SELECT * FROM creeps WHERE user_id = "+id+"";
        String query_mentor = "SELECT * FROM creeps WHERE user_id = "+id+"";
        String query_student = "SELECT * FROM creeps WHERE user_id = "+id+"";
        try {
            if (ds.executeQuery(query_creep).next()){
                return "creeps";
            } else if (ds.executeQuery(query_mentor).next()){
                return "mentor";
            } else if (ds.executeQuery(query_student).next()){
                return "student";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }
}

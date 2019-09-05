package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.UsersDAO;
import com.pixel.model.User;

import java.sql.*;

public class UserDAOI implements UsersDAO {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public UserDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    public int getIdFromCredentials(String name, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE name = ? AND password = ? LIMIT 1";
        this.ps = c.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getInt("id");
        }
        return -1;
    }

    public boolean checkIfUserIsCreep(int id) throws SQLException {
        String query = "SELECT * from creeps WHERE user_id = "+id+"";
        this.stmt = c.createStatement();
        this.rs = stmt.executeQuery(query);
        return rs.next();
    }

    public boolean checkIfUserIsMentor(int id) throws SQLException {
        String query = "SELECT * from mentors WHERE user_id = "+id+"";
        this.stmt = c.createStatement();
        this.rs = stmt.executeQuery(query);
        return rs.next();
    }

    public boolean checkIfUserIsStudent(int id) throws SQLException {
        String query = "SELECT * from students WHERE user_id = "+id+"";
        this.stmt = c.createStatement();
        this.rs = stmt.executeQuery(query);
        return rs.next();
    }
}

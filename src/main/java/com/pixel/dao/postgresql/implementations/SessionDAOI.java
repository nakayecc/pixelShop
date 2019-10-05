package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.Session;

import java.sql.*;

public class SessionDAOI implements Session {
    private Connection c;
    public SessionDAOI(Connection connection) {
        this.c = connection;
    }




    @Override
    public void createSession(String session, int userId) throws SQLException {
        PreparedStatement ps;
        String query = "INSERT INTO session(sessionid, id_user)  VALUES (?,?)";
        ps = c.prepareStatement(query);
        ps.setString(1, session);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }

    @Override
    public void deleteSessionById(int userId) throws SQLException {
        Statement stmt;
        String query = "DELETE FROM session WHERE id_user = " + userId + "";
        stmt = c.createStatement();
        stmt.execute(query);
    }

    @Override
    public boolean isCurrentSession(String session) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM session WHERE sessionid = ?";
        ps = c.prepareStatement(query);
        ps.setString(1, session);
        rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public int getUserId(String session) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM session WHERE sessionid = ? LIMIT 1";
        ps = c.prepareStatement(query);
        ps.setString(1, session);
        rs = ps.executeQuery();
        if (rs.next()){
            return rs.getInt("id_user");
        }
        return 0;

    }

}

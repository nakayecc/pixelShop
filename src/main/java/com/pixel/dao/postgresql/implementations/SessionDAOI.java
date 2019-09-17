package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.Session;

import java.sql.*;

public class SessionDAOI implements Session {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public SessionDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }




    @Override
    public void createSession(String session, int userId) throws SQLException {
        String query = "INSERT INTO session(sessionid, id_user)  VALUES (?,?)";
        this.ps = c.prepareStatement(query);
        ps.setString(1, session);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }

    @Override
    public void deleteSessionById(int userId) throws SQLException {
        String query = "DELETE FROM session WHERE id_user = " + userId + "";
        this.stmt = c.createStatement();
        stmt.execute(query);
    }

    @Override
    public boolean isCurrentSession(String session) {
        return false;
    }

    @Override
    public int getUserId(String session) throws SQLException {
        String query = "SELECT * FROM session WHERE sessionid = ?";
        this.ps = c.prepareStatement(query);
        ps.setString(1, session);
        rs = ps.executeQuery();
        if (rs.next()){
            return rs.getInt("id_user");
        }
        return 0;

    }

}

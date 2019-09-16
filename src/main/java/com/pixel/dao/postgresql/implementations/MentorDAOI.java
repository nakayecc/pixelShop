package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.MentorDAO;
import com.pixel.model.Mentor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorDAOI extends UserDAOI implements MentorDAO {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public MentorDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    @Override
    public List<Mentor> getListFull() throws SQLException {
        this.rs = getRSAllMentors();
        return getListFromRS(rs);
    }

    public List<Mentor> getListByValue(String valeName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valeName, value));
    }

    @Override
    public boolean save(Mentor mentor) throws SQLException {
        String query = "" +
                "WITH ins1 AS (" +
                "    INSERT INTO users(name, password, role_name)" +
                "        VALUES (?, ?, ?)" +
                "        RETURNING id AS user_id" +
                ") " +
                "INSERT INTO mentors (user_id, class_id)" +
                "SELECT user_id, ? FROM ins1;";
        this.ps = c.prepareStatement(query);
        ps.setString(1, mentor.getName());
        ps.setString(2, mentor.getPassword());
        ps.setString(3, mentor.getRoleName());
        ps.setInt(4, mentor.getClass_id());
        return ps.execute();
    }

    @Override
    public boolean update(Mentor mentor) throws SQLException {
        int id = mentor.getId();

        updateInUserTable(id,mentor.getName(),mentor.getPassword(),mentor.getRoleName());

        String query = "UPDATE mentors SET class_id = ? WHERE user_id = "+id+"";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, mentor.getClass_id());
        ps.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(Mentor mentor) throws SQLException {
        this.stmt = c.createStatement();
        String query = "DELETE FROM mentors WHERE user_id = " + mentor.getId() + "; DELETE FROM users WHERE id = " + mentor.getId() +"";
        return stmt.execute(query);
    }

    @Override
    public Mentor getById(int id) throws SQLException {
        String query = "select id, name,password,role_name, class_id from users " +
                "left join mentors s on users.id = s.user_id WHERE (id = s.user_id AND (id = ?));";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        String query = "select id, name,password,role_name, class_id from users " +
                "left join mentors s on users.id = s.user_id WHERE (id = s.user_id AND ("+valueName+" = ?));";
        this.ps = c.prepareStatement(query);
        ps.setString(1, value);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    private ResultSet getRSAllMentors() throws SQLException {
        String query = "select id, name,password,role_name, class_id from users " +
                "left join mentors s on users.id = s.user_id WHERE id = s.user_id ;";
        Statement stmt = c.createStatement();
        return stmt.executeQuery(query);
    }

    private List<Mentor> getListFromRS(ResultSet rs) throws SQLException {
        List<Mentor> list = new ArrayList<>();
        while (rs.next()) list.add(extractMentorFromRS(rs));
        rs.close();
        c.close();
        return list;

    }

    private Mentor extractMentorFromRS(ResultSet rs) throws SQLException {
        return new Mentor(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("role_name"),
                rs.getInt("class_id"));
    }


    private void close() throws SQLException {
        ps.close();
        stmt.close();
        c.close();
    }

}

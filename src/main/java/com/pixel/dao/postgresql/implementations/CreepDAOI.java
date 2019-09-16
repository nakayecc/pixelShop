package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.CreepDAO;
import com.pixel.dao.postgresql.interfaces.MentorDAO;
import com.pixel.model.Creep;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreepDAOI extends UserDAOI implements CreepDAO {
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public CreepDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    @Override
    public List<Creep> getListFull() throws SQLException {
        this.rs = getRSAllCreeps();
        return getListFromRS(rs);
    }

    public List<Creep> getListByValue(String valeName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valeName, value));
    }

    @Override
    public boolean save(Creep creep) throws SQLException {
        String query = "" +
                "WITH ins1 AS (" +
                "    INSERT INTO users(name, password, role_name)" +
                "        VALUES (?, ?, ?)" +
                "        RETURNING id AS user_id" +
                ") " +
                "INSERT INTO creeps (user_id)" +
                "SELECT user_id, ? FROM ins1;";
        this.ps = this.c.prepareStatement(query);
        ps.setString(1, creep.getName());
        ps.setString(2, creep.getPassword());
        ps.setString(3, creep.getRoleName());
        return ps.execute();
    }

    @Override
    public boolean update(Creep creep) throws SQLException {
        updateInUserTable(creep.getId(),creep.getName(), creep.getPassword(), creep.getRoleName());
        return true;
    }

    @Override
    public boolean delete(Creep creep) throws SQLException {
        this.stmt = this.c.createStatement();
        String query = "DELETE FROM mentors WHERE user_id = " + creep.getId() + "; DELETE FROM users WHERE id = " + creep.getId() +"";
        return stmt.execute(query);
    }

    @Override
    public Creep getById(int id) throws SQLException {
        String query = "select id, name,password,role_name, class_id from users " +
                "left join mentors s on users.id = s.user_id WHERE (id = s.user_id AND (id = ?));";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        String query = "select id, name,password,role_name from users " +
                "left join creeps s on users.id = s.user_id WHERE (id = s.user_id AND ("+valueName+" = ?));";
        this.ps = c.prepareStatement(query);
        ps.setString(1, value);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    private ResultSet getRSAllCreeps() throws SQLException {
        String query = "select id, name,password,role_name from users " +
                "left join creeps s on users.id = s.user_id WHERE id = s.user_id ;";
        Statement stmt = c.createStatement();
        return stmt.executeQuery(query);
    }

    private List<Creep> getListFromRS(ResultSet rs) throws SQLException {
        List<Creep> list = new ArrayList<>();
        while (rs.next()) list.add(extractCreepsFromRS(rs));
        rs.close();
        c.close();
        return list;

    }

    private Creep extractCreepsFromRS(ResultSet rs) throws SQLException {
        return new Creep(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("role_name"));
    }


    private void close() throws SQLException {
        ps.close();
        stmt.close();
        c.close();
    }

}

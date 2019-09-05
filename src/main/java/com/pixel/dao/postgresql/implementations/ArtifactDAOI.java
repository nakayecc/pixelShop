package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.ArtifactDao;
import com.pixel.model.Artifact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAOI implements ArtifactDao {

    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public ArtifactDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    @Override
    public List<Artifact> getListFull() throws SQLException {
        this.rs = getAllArtifactRS();
        return getListFromRS(rs);
    }

    @Override
    public List<Artifact> getListBy(String valueName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public boolean updateArtifact(Artifact artifact, int id) {
        return false;
    }

    @Override
    public boolean insertArtifact(Artifact artifact) {
        return false;
    }

    @Override
    public boolean deleteArtifact(Artifact artifact) {
        return false;
    }

    private List<Artifact> getListFromRS(ResultSet rs) throws SQLException {
        List<Artifact> artifactList = new ArrayList<>();
        while (rs.next()) artifactList.add(extractArtifactFromRS(rs));
        rs.close();
        c.close();
        return artifactList;

    }

    private Artifact extractArtifactFromRS(ResultSet rs) throws SQLException {
        return new Artifact(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getBoolean("is_global"));
    }

    private ResultSet getAllArtifactRS() throws SQLException {
        String query = " SELECT id, name, description, price, is_global FROM artifacts";
        Statement stmt = c.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        String query = "select id, name, description, price, is_global from artifacts " +
                "WHERE " + valueName + " = ?));";
        this.ps = c.prepareStatement(query);
        ps.setString(1, value);
        return ps.executeQuery();
    }
}

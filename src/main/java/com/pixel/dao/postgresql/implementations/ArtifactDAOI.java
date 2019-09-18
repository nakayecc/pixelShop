package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.ArtifactDao;
import com.pixel.model.Artifact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAOI implements ArtifactDao {

    private Connection connection;

    public ArtifactDAOI(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Artifact> getListFull() throws SQLException {
        ResultSet resultSet;
        resultSet = getAllArtifactRS();
        return getListFromRS(resultSet);
    }

    @Override
    public List<Artifact> getListBy(String valueName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public boolean updateArtifact(Artifact artifact) throws SQLException {
        PreparedStatement preparedStatement;

        String query = "UPDATE artifacts SET name = ?, description = ?, price = ?,is_global = ? " +
                "WHERE id = " + artifact.getId() + "";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, artifact.getName());
        preparedStatement.setString(2, artifact.getDescription());
        preparedStatement.setInt(3, artifact.getPrice());
        preparedStatement.setBoolean(4, artifact.isGlobal());
        int i = preparedStatement.executeUpdate();
        if(i == 1){
            preparedStatement.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean insertArtifact(Artifact artifact) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO artifacts(name, description, price, is_global) VALUES (?,?,?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, artifact.getName());
        preparedStatement.setString(2, artifact.getDescription());
        preparedStatement.setInt(3, artifact.getPrice());
        preparedStatement.setBoolean(4, artifact.isGlobal());
        int i = preparedStatement.executeUpdate();
        if(i == 1){
            preparedStatement.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteArtifact(Artifact artifact) throws SQLException {
        Statement statement;
        String query = "DELETE FROM artifacts WHERE id = " + artifact.getId() + "";
        statement = connection.createStatement();
        return statement.execute(query);

    }

    private List<Artifact> getListFromRS(ResultSet rs) throws SQLException {
        List<Artifact> artifactList = new ArrayList<>();
        while (rs.next()) artifactList.add(extractArtifactFromRS(rs));
        return artifactList;

    }

    @Override
    public Artifact getById(int id) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name, description, price, is_global from artifacts " +
                "WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        return getListFromRS(rs).get(0);
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
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name, description, price, is_global from artifacts " +
                "WHERE " + valueName + " = ?));";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        return preparedStatement.executeQuery();
    }

    public void connClose() throws SQLException {
        connection.close();

    }
}

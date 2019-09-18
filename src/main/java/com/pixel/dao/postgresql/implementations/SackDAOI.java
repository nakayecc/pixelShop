package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.interfaces.SackDAO;
import com.pixel.model.Artifact;
import com.pixel.model.Sack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SackDAOI implements SackDAO {
    private Connection connection;

    public SackDAOI(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Sack> getListFull() throws SQLException {
        ResultSet resultSet;
        resultSet = getAllArtifactRS();
        return getListFromRS(resultSet);
    }

    @Override
    public List<Sack> getListBy(String valueName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public List<Sack> getListBy(String valueName, int value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public boolean updateSack(Sack sack) throws SQLException {
        PreparedStatement preparedStatement;

        String query = "UPDATE sacks SET user_id = ?, id = ?  " +
                "WHERE id = " + sack.getId() + "";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, sack.getUserId());
        preparedStatement.setInt(2, sack.getId());
        int i = preparedStatement.executeUpdate();
        if (i == 1) {
            preparedStatement.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean insertSack(Sack sack) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO sacks(user_id, id) VALUES (?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, sack.getUserId());
        preparedStatement.setInt(2, sack.getId());
        int i = preparedStatement.executeUpdate();
        if (i == 1) {
            preparedStatement.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSack(Sack sack) throws SQLException {
        Statement statement;
        String query = "DELETE FROM sacks WHERE id = " + sack.getId() + "";
        statement = connection.createStatement();
        return statement.execute(query);

    }

    private List<Sack> getListFromRS(ResultSet rs) throws SQLException {
        List<Sack> sacksList = new ArrayList<>();
        while (rs.next()) sacksList.add(extractArtifactFromRS(rs));
        return sacksList;

    }

    @Override
    public Sack getById(int id) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select user_id, id from sacks " +
                "WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private Sack extractArtifactFromRS(ResultSet rs) throws SQLException {
        return new Sack(
                rs.getInt("id"),
                rs.getInt("user_id"));
    }

    private ResultSet getAllArtifactRS() throws SQLException {
        String query = " SELECT user_id, id FROM sacks";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select user_id,id from sacks " +
                "WHERE " + valueName + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        return preparedStatement.executeQuery();
    }

    private ResultSet getRSByValue(String valueName, int value) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select user_id,id from sacks " +
                "WHERE " + valueName + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, value);
        return preparedStatement.executeQuery();
    }


}

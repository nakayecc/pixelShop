package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.interfaces.SackInventoryDAO;
import com.pixel.model.SackInventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SackInventoryDAOI implements SackInventoryDAO {
    private Connection connection;

    public SackInventoryDAOI(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SackInventory> getListFull() throws SQLException {
        ResultSet resultSet;
        resultSet = getAllArtifactRS();
        return getListFromRS(resultSet);
    }

    @Override
    public List<SackInventory> getListBy(String valueName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public List<SackInventory> getListBy(String valueName, int value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public boolean updateSackInventory(SackInventory sackInventory) throws SQLException {
        PreparedStatement preparedStatement;

        String query = "UPDATE sacks_inventory SET user_id =?,artifact_id = ?,ready = ?, id = ?, price = ?  " +
                "WHERE id = " + sackInventory.getId() + "";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, sackInventory.getUserId());
        preparedStatement.setInt(2, sackInventory.getArtifactId());
        preparedStatement.setBoolean(3, sackInventory.isReady());
        preparedStatement.setInt(4, sackInventory.getId());
        preparedStatement.setInt(5, sackInventory.getPrice());
        int i = preparedStatement.executeUpdate();
        if (i == 1) {
            preparedStatement.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean insertSackInventory(SackInventory sackInventory) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO sacks_inventory(user_id, artifact_id, ready, id, price) VALUES (?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, sackInventory.getUserId());
        preparedStatement.setInt(2, sackInventory.getArtifactId());
        preparedStatement.setBoolean(3, sackInventory.isReady());
        preparedStatement.setInt(4, sackInventory.getId());
        preparedStatement.setInt(5, sackInventory.getPrice());
        int i = preparedStatement.executeUpdate();
        if (i == 1) {
            preparedStatement.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSackInventory(SackInventory sackInventory) throws SQLException {
        Statement statement;
        String query = "DELETE FROM sacks_inventory WHERE id = " + sackInventory.getId() + "";
        statement = connection.createStatement();
        return statement.execute(query);

    }

    private List<SackInventory> getListFromRS(ResultSet rs) throws SQLException {
        List<SackInventory> sacksList = new ArrayList<>();
        while (rs.next()) sacksList.add(extractArtifactFromRS(rs));
        return sacksList;

    }

    @Override
    public SackInventory getById(int id) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select user_id, artifact_id, ready, id, price from sacks_inventory " +
                "WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private SackInventory extractArtifactFromRS(ResultSet rs) throws SQLException {
        return new SackInventory(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("artifact_id"),
                rs.getBoolean("ready"),
                rs.getInt("price"));

    }

    private ResultSet getAllArtifactRS() throws SQLException {
        String query = " SELECT user_id, artifact_id, ready, id, price FROM sacks_inventory";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "SELECT user_id, artifact_id, ready, id, price FROM sacks_inventory " +
                "WHERE " + valueName + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        return preparedStatement.executeQuery();
    }

    private ResultSet getRSByValue(String valueName, int value) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "SELECT user_id, artifact_id, ready, id, price FROM sacks_inventory " +
                "WHERE " + valueName + " = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, value);
        return preparedStatement.executeQuery();
    }
    public void connClose() throws SQLException {
        connection.close();

    }
}

package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.QuestCategoryDAO;
import com.pixel.model.QuestCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestCategoryDAOI implements QuestCategoryDAO {

    private Connection connection;

    public QuestCategoryDAOI(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<QuestCategory> getListFull() throws SQLException {
        ResultSet resultSet;
        resultSet = getAllQuestCategoryRS();
        return getListFromRS(resultSet);
    }

    @Override
    public List<QuestCategory> getListBy(String valueName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public boolean updateQuestCategory(QuestCategory questCategory) throws SQLException {
        PreparedStatement preparedStatement;

        String query = "UPDATE quests_categories SET name = ? " +
                "WHERE id = " + questCategory.getId() + "";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, questCategory.getName());
        int i = preparedStatement.executeUpdate();

        return i == 1;
    }

    @Override
    public boolean insertQuestCategory(QuestCategory questCategory) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO quests_categories(name) VALUES (?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, questCategory.getName());
        int i = preparedStatement.executeUpdate();
        return i == 1;
    }

    @Override
    public boolean deleteQuestCategory(QuestCategory questCategory) throws SQLException {
        Statement statement;
        String query = "DELETE FROM artifacts WHERE id = " + questCategory.getId() + "";
        statement = connection.createStatement();
        return statement.execute(query);

    }

    private List<QuestCategory> getListFromRS(ResultSet rs) throws SQLException {
        List<QuestCategory> artifactList = new ArrayList<>();
        while (rs.next()) artifactList.add(extractArtifactFromRS(rs));
        return artifactList;

    }

    @Override
    public QuestCategory getById(int id) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name from quests_categories " +
                "WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private QuestCategory extractArtifactFromRS(ResultSet rs) throws SQLException {
        return new QuestCategory(
                rs.getInt("id"),
                rs.getString("name"));
    }

    private ResultSet getAllQuestCategoryRS() throws SQLException {
        String query = " SELECT id, name FROM quests_categories";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name from quests_categories " +
                "WHERE " + valueName + " = ?));";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        return preparedStatement.executeQuery();
    }
}


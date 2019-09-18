package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.QuestDAO;
import com.pixel.model.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAOI implements QuestDAO {

    private Connection connection;

    public QuestDAOI(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Quest> getListFull() throws SQLException {
        ResultSet resultSet;
        resultSet = getAllQuestRS();
        return getListFromRS(resultSet);
    }

    public List<Quest> getListActive() throws SQLException {
        ResultSet rs = getActiveQuestRS();
        return getListFromRS(rs);
    }

    @Override
    public List<Quest> getListBy(String valueName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public boolean updateQuest(Quest quest) throws SQLException {

        PreparedStatement preparedStatement;

        String query = "UPDATE quests SET name = ?, exp = ?, category_id = ?, description = ? " +
                "WHERE id = " + quest.getId() + "";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, quest.getName());
        preparedStatement.setInt(2, quest.getExp());
        preparedStatement.setInt(3, quest.getCategoryId());
        preparedStatement.setString(4, quest.getDescription());
        int i = preparedStatement.executeUpdate();

        return i == 1;
    }

    @Override
    public boolean insertQuest(Quest quest) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO quests(name, exp, category_id, description)  VALUES (?,?,?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, quest.getName());
        preparedStatement.setInt(2, quest.getExp());
        preparedStatement.setInt(3, quest.getCategoryId());
        preparedStatement.setString(4, quest.getDescription());
        int i = preparedStatement.executeUpdate();
        return i == 1;
    }

    @Override
    public boolean deleteQuest(Quest artifact) throws SQLException {
        Statement statement;
        String query = "DELETE FROM quests WHERE id = " + artifact.getId() + "";
        statement = connection.createStatement();
        return statement.execute(query);

    }

    private List<Quest> getListFromRS(ResultSet rs) throws SQLException {
        List<Quest> questList = new ArrayList<>();
        while (rs.next()) questList.add(extractQuestFromRS(rs));
        return questList;

    }

    @Override
    public Quest getById(int id) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name, exp, category_id, description from quests " +
                "WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private Quest extractQuestFromRS(ResultSet rs) throws SQLException {

        return new Quest(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("exp"),
                rs.getInt("category_id"));
    }

    private ResultSet getAllQuestRS() throws SQLException {
        String query = " SELECT id, name, exp, category_id, description FROM quests";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getActiveQuestRS() throws SQLException {
        String query = " SELECT id, name, exp, category_id, description FROM quests WHERE is_active = true";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name, exp, category_id, description FROM quests " +
                "WHERE " + valueName + " = ?));";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, value);
        return preparedStatement.executeQuery();
    }
    public void connClose() throws SQLException {
        connection.close();

    }

}

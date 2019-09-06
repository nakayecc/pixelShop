package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.QuestCategoryDAO;
import com.pixel.model.QuestCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestCategoryDAOI implements QuestCategoryDAO {

    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public QuestCategoryDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    @Override
    public List<QuestCategory> getListFull() throws SQLException {
        this.rs = getAllQuestCategoryRS();
        return getListFromRS(rs);
    }

    @Override
    public List<QuestCategory> getListBy(String valueName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public boolean updateQuestCategory(QuestCategory questCategory) throws SQLException {

        String query = "UPDATE quests_categories SET name = ? " +
                "WHERE id = " + questCategory.getId() + "";
        this.ps = c.prepareStatement(query);
        ps.setString(1, questCategory.getName());
        int i = ps.executeUpdate();

        return i == 1;
    }

    @Override
    public boolean insertQuestCategory(QuestCategory questCategory) throws SQLException {
        String query = "INSERT INTO quests_categories(name) VALUES (?)";
        this.ps = c.prepareStatement(query);
        ps.setString(1, questCategory.getName());
        int i = ps.executeUpdate();
        return i == 1;
    }

    @Override
    public boolean deleteQuestCategory(QuestCategory questCategory) throws SQLException {
        String query = "DELETE FROM artifacts WHERE id = " + questCategory.getId() + "";
        this.stmt = c.createStatement();
        return stmt.execute(query);

    }

    private List<QuestCategory> getListFromRS(ResultSet rs) throws SQLException {
        List<QuestCategory> artifactList = new ArrayList<>();
        while (rs.next()) artifactList.add(extractArtifactFromRS(rs));
        return artifactList;

    }

    @Override
    public QuestCategory getById(int id) throws SQLException {
        String query = "select id, name from quests_categories " +
                "WHERE id = ?";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private QuestCategory extractArtifactFromRS(ResultSet rs) throws SQLException {
        return new QuestCategory(
                rs.getInt("id"),
                rs.getString("name"));
    }

    private ResultSet getAllQuestCategoryRS() throws SQLException {
        String query = " SELECT id, name FROM quests_categories";
        Statement stmt = c.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        String query = "select id, name from quests_categories " +
                "WHERE " + valueName + " = ?));";
        this.ps = c.prepareStatement(query);
        ps.setString(1, value);
        return ps.executeQuery();
    }
}


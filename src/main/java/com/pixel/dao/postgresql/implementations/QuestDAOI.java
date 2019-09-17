package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.QuestDAO;
import com.pixel.model.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAOI implements QuestDAO {

    public QuestDAOI() {
        this.c = new PostgreSQLJDBC().getConnection();
    }

    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    @Override
    public List<Quest> getListFull() throws SQLException {
        this.rs = getAllQuestRS();
        return getListFromRS(rs);
    }

    public List<Quest> getListActive() throws SQLException {
        this.rs = getActiveQuestRS();
        return getListFromRS(rs);
    }

    @Override
    public List<Quest> getListBy(String valueName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valueName, value));
    }

    @Override
    public boolean updateQuest(Quest quest) throws SQLException {

        String query = "UPDATE quests SET name = ?, exp = ?, category_id = ?, description = ? " +
                "WHERE id = " + quest.getId() + "";
        this.ps = c.prepareStatement(query);
        ps.setString(1, quest.getName());
        ps.setInt(2, quest.getExp());
        ps.setInt(3, quest.getCategoryId());
        ps.setString(4, quest.getDescription());
        int i = ps.executeUpdate();

        return i == 1;
    }

    @Override
    public boolean insertQuest(Quest quest) throws SQLException {
        String query = "INSERT INTO quests(name, exp, category_id, description)  VALUES (?,?,?,?)";
        this.ps = c.prepareStatement(query);
        ps.setString(1, quest.getName());
        ps.setInt(2, quest.getExp());
        ps.setInt(3, quest.getCategoryId());
        ps.setString(4, quest.getDescription());
        int i = ps.executeUpdate();
        return i == 1;
    }

    @Override
    public boolean deleteQuest(Quest artifact) throws SQLException {
        String query = "DELETE FROM quests WHERE id = " + artifact.getId() + "";
        this.stmt = c.createStatement();
        return stmt.execute(query);

    }

    private List<Quest> getListFromRS(ResultSet rs) throws SQLException {
        List<Quest> questList = new ArrayList<>();
        while (rs.next()) questList.add(extractQuestFromRS(rs));
        c.close();
        return questList;

    }

    @Override
    public Quest getById(int id) throws SQLException {
        String query = "select id, name, exp, category_id, description from quests " +
                "WHERE id = ?";
        this.ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
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
        Statement stmt = c.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getActiveQuestRS() throws SQLException {
        String query = " SELECT id, name, exp, category_id, description FROM quests WHERE is_active = true";
        Statement stmt = c.createStatement();
        return stmt.executeQuery(query);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        String query = "select id, name, exp, category_id, description FROM quests " +
                "WHERE " + valueName + " = ?));";
        this.ps = c.prepareStatement(query);
        ps.setString(1, value);
        return ps.executeQuery();
    }

}

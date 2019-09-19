package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.StudentDAO;
import com.pixel.model.Quest;
import com.pixel.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StudentDAOI implements StudentDAO {
    private Connection connection;

    public StudentDAOI(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> getListFull() throws SQLException {
        ResultSet resultSet;
        resultSet = getRSAllStudents();
        return getListFromRS(resultSet);
    }

    public List<Student> getListByValue(String valeName, String value) throws SQLException {
        return getListFromRS(getRSByValue(valeName, value));
    }

    public String getMentorName(Student s) throws SQLException {
        return new MentorDAOI(connection).getById(s.getMentor_id()).getName();
    }

    public String getClassName(Student s) throws SQLException {
        return new ClassesDAOI(connection).getClassById(s.getCass_id()).getName();
    }



    public HashMap<Quest, Integer> getQuestCompleted(Student s) throws SQLException {
        int id = s.getId();
        String query = "select quest_id from quests_completed WHERE (user_id = ?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        HashMap<Quest, Integer> questsCompleted = new HashMap<Quest, Integer>();
        while (rs.next()) {
            int questId = rs.getInt("quest_id");
            Quest quest = new QuestDAOI(connection).getById(questId);
            int count = questsCompleted.getOrDefault(quest, 0);
            questsCompleted.put(quest, count + 1);
        }
        return questsCompleted;
    }

    public int getExperience(Student s) throws SQLException {
        PreparedStatement preparedStatement;
        int id = s.getId();
        String query = "select exp_gained from quests_completed WHERE (user_id = ?);";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        int total_exp = 0;
        while (rs.next()) {
            total_exp += rs.getInt("exp_gained");
        }
        return total_exp;
    }

    @Override
    public boolean save(Student s) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "" +
                "WITH ins1 AS (" +
                "    INSERT INTO users(name, password, role_name)" +
                "        VALUES (?, ?, ?)" +
                "        RETURNING id AS user_id" +
                ")" +
                "INSERT INTO students (user_id, mentor_id, class_id)" +
                "SELECT user_id, ?, ? FROM ins1;" +
                "INSERT INTO sacks (user_id)" +
                "VALUES (user_id)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, s.getName());
        preparedStatement.setString(2, s.getPassword());
        preparedStatement.setString(3, s.getRoleName());
        preparedStatement.setInt(4, s.getMentor_id());
        preparedStatement.setInt(5, s.getCass_id());
        return preparedStatement.execute();
    }

    @Override
    public boolean update(Student s) throws SQLException {
        PreparedStatement preparedStatement;
        int id = s.getId();

        String query = "UPDATE users SET name = ?, password = ?, role_name = ? WHERE id = "+id+"";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, s.getName());
        preparedStatement.setString(2, s.getPassword());
        preparedStatement.setString(3, s.getRoleName());
        preparedStatement.executeUpdate();

        query = "UPDATE students SET mentor_id = ?, class_id = ? WHERE user_id = "+id+"";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, s.getMentor_id());
        preparedStatement.setInt(2, s.getCass_id());
        preparedStatement.executeUpdate();

        return true;
    }

    @Override
    public boolean delete(Student s) throws SQLException {
        Statement statement;
        statement = connection.createStatement();
        String query = "DELETE FROM students WHERE user_id = " + s.getId() + "; DELETE FROM users WHERE id = " + s.getId() +"";
        return statement.execute(query);
    }

    @Override
    public Student getById(int id) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name,password,role_name,mentor_id,class_id from users " +
                "left join students s on users.id = s.user_id WHERE (id = s.user_id AND (id = ?));";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        return getListFromRS(rs).get(0);
    }

    private ResultSet getRSByValue(String valueName, String value) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "select id, name,password,role_name,mentor_id,class_id from users " +
                "left join students s on users.id = s.user_id WHERE (id = s.user_id AND ("+valueName+" = ?));";
        preparedStatement = connection.prepareStatement(query);
//        ps.setString(1);
        preparedStatement.setString(1, value);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    private ResultSet getRSAllStudents() throws SQLException {
        String query = "select id, name,password,role_name,mentor_id,class_id from users " +
                "left join students s on users.id = s.user_id WHERE id = s.user_id ;";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    private List<Student> getListFromRS(ResultSet rs) throws SQLException {
        List<Student> list = new ArrayList<>();
        while (rs.next()) list.add(extractStudentFromRS(rs));
        return list;

    }

    private Student extractStudentFromRS(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("role_name"),
                rs.getInt("mentor_id"),
                rs.getInt("class_id"));
    }
    public void connClose() throws SQLException {
        connection.close();

    }
}

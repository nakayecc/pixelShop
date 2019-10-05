package com.pixel.dao.postgresql.implementations;

import com.pixel.model.Quest;
import com.pixel.model.Student;

import java.sql.*;

public class QuestCompletedDAOI {

    private Connection connection;

    public QuestCompletedDAOI(Connection connection) {
        this.connection = connection;
    }

    public boolean completeQuest(Student student, Quest quest) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "INSERT INTO quests_completed(user_id, quest_id, exp_gained)  VALUES (?,?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, student.getId());
        preparedStatement.setInt(2, quest.getId());
        preparedStatement.setInt(3, quest.getExp());
        int i = preparedStatement.executeUpdate();
        return i == 1;
    }

}

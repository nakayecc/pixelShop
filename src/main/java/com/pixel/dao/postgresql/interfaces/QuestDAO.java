package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.Artifact;
import com.pixel.model.Quest;

import java.sql.SQLException;
import java.util.List;

public interface QuestDAO {

    Quest getById(int id) throws SQLException;
    List<Quest> getListFull() throws SQLException;
    List<Quest> getListBy(String valueName, String value) throws SQLException;
    boolean updateQuest(Quest quest) throws SQLException;
    boolean insertQuest(Quest quest) throws SQLException;
    boolean deleteQuest(Quest quest) throws SQLException;

}

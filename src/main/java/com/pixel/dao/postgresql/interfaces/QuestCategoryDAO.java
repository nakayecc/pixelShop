package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.Quest;
import com.pixel.model.QuestCategory;

import java.sql.SQLException;
import java.util.List;

public interface QuestCategoryDAO {

    QuestCategory getById(int id) throws SQLException;
    List<QuestCategory> getListFull() throws SQLException;
    List<QuestCategory> getListBy(String valueName, String value) throws SQLException;
    boolean updateQuestCategory(QuestCategory questCategory) throws SQLException;
    boolean insertQuestCategory(QuestCategory questCategory) throws SQLException;
    boolean deleteQuestCategory(QuestCategory questCategory) throws SQLException;


}

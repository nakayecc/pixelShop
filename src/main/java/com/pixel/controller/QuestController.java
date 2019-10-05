package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.QuestCategoryDAOI;
import com.pixel.dao.postgresql.implementations.QuestDAOI;
import com.pixel.model.Quest;
import com.pixel.model.QuestCategory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestController {
    private QuestDAOI questDAOI;
    private QuestCategoryDAOI questCategoryDAOI;

    public QuestController(QuestDAOI questDAOI, QuestCategoryDAOI questCategoryDAOI) {
        this.questDAOI = questDAOI;
        this.questCategoryDAOI = questCategoryDAOI;
    }

    public List<Quest> getQuestList()  {
        List<Quest> questList = new ArrayList<>();
        try {
            questList = questDAOI.getListFull();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questList;
    }

    public List<Quest> getActiveQuest(){
        List<Quest> questList = new ArrayList<>();
        try {
            questList = questDAOI.getListActive();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questList;
    }

    public int getNumberOfActiveQuest(){
        try {
            return questDAOI.getListActive().size();
        } catch (SQLException e) {
            return 0;
        }
    }

    public List<QuestCategory> getQuestCategory(){
        List<QuestCategory> questCategoryList = new ArrayList<>();

        try {
            questCategoryList = questCategoryDAOI.getListFull();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questCategoryList;
    }
}

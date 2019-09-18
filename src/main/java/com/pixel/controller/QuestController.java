package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.QuestDAOI;
import com.pixel.model.Quest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestController {
    private QuestDAOI questDAOI;

    public QuestController(QuestDAOI questDAOI) {
        this.questDAOI = questDAOI;
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

    public int getNumberOfActiveQuest(){
        try {
            return new QuestDAOI().getListActive().size();
        } catch (SQLException e) {
            return 0;
        }
    }
}

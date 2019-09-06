package com.pixel;

import com.pixel.dao.postgresql.implementations.ArtifactDAOI;
import com.pixel.dao.postgresql.implementations.QuestDAOI;
import com.pixel.model.Quest;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {

        ArtifactDAOI artifactDAOI = new ArtifactDAOI();
        QuestDAOI questDAOI = new QuestDAOI();

        try {
            System.out.println(questDAOI.getListFull());
            //questDAOI.insertQuest(new Quest("quest2","test Desc",12,2));
           // questDAOI.deleteQuest(questDAOI.getById(8));
            Quest quest = questDAOI.getById(3);
            quest.setExp(666);
            questDAOI.updateQuest(quest);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

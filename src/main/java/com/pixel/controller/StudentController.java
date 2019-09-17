package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.LevelsDAOI;
import com.pixel.dao.postgresql.implementations.StudentDAOI;
import com.pixel.model.Quest;
import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.*;

import static java.util.Collections.emptyMap;

public class StudentController {
    StudentDAOI studentDAOI = new StudentDAOI();


    public List<Student> getStudentList() throws SQLException {
        return studentDAOI.getListFull();
    }

    public Student getStudent(int id) throws SQLException {
        return studentDAOI.getById(id);
    }

    public String getStudentName(Student student) {
        return student.getName();
    }

    public int getStudentExperience(Student student) {
        try {
            return new StudentDAOI().getExperience(student);
        } catch (SQLException e) {
            return 0;
        }
    }

    public String getUserLevel(Student student) {
        int experience = getStudentExperience(student);
        HashMap<String, Integer> levels = null;
        try {
            levels = new LevelsDAOI().getLevelMap();
        } catch (SQLException e) {
            return "";
        }
        int topRank = 0;
        String rankName = "";
        for (Map.Entry<String, Integer> entry : levels.entrySet()){
            int expNeeded = entry.getValue();
            if (expNeeded>topRank && experience>expNeeded){
                topRank = expNeeded;
                rankName = entry.getKey();
            }
        }
        return rankName;
    }

    public HashMap<Quest, Integer> getAllQuestCompleted(Student student){
        try {
            return new StudentDAOI().getQuestCompleted(student);
        } catch (SQLException e) {
            return new HashMap<>();
        }
    }

    public float getPercentageOfCompleted(Student student){
        int questCompleted = getUniqueQuestCompleted(student);
        int totalQuests = new QuestController().getNumberOfActiveQuest();
        return (float) questCompleted/totalQuests*100;
    }

    public int getUniqueQuestCompleted(Student student){
        try {
            return new StudentDAOI().getQuestCompleted(student).keySet().size();
        } catch (SQLException e) {
            return 0;
        }
    }

}





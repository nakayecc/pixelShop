package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.*;
import com.pixel.model.Artifact;
import com.pixel.model.Quest;
import com.pixel.model.SackInventory;
import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.*;

import static java.util.Collections.emptyMap;

public class
StudentController {
    StudentDAOI studentDAOI ;
    LevelsDAOI levelsDAOI;
    QuestDAOI questDAOI;
    ClassesDAOI classesDAOI;
    ArtifactDAOI artifactDAOI;
    SackInventoryDAOI sackInventoryDAOI;

    public StudentController(StudentDAOI studentDAOI, LevelsDAOI levelsDAOI, QuestDAOI questDAOI, ClassesDAOI classesDAOI, ArtifactDAOI artifactDAOI, SackInventoryDAOI sackInventoryDAOI) {
        this.studentDAOI = studentDAOI;
        this.levelsDAOI = levelsDAOI;
        this.questDAOI = questDAOI;
        this.classesDAOI = classesDAOI;
        this.artifactDAOI = artifactDAOI;
        this.sackInventoryDAOI = sackInventoryDAOI;
    }

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
            return studentDAOI.getExperience(student);
        } catch (SQLException e) {
            return 0;
        }
    }

    public String getUserLevel(Student student) {
        int experience = getStudentExperience(student);
        HashMap<String, Integer> levels = null;
        try {
            levels = levelsDAOI.getLevelMap();
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
            return studentDAOI.getQuestCompleted(student);
        } catch (SQLException e) {
            return new HashMap<>();
        }
    }

    public float getPercentageOfCompleted(Student student){
        int questCompleted = getUniqueQuestCompleted(student);
        int totalQuests = new QuestController(questDAOI).getNumberOfActiveQuest();
        return (float) questCompleted/totalQuests*100;
    }

    public String getMentorName(Student student){
        try {
            return studentDAOI.getMentorName(student);
        } catch (SQLException e) {
            return "";
        }
    }

    public String getClassName(Student student){
        try {
            return classesDAOI.getClassById(student.getCass_id()).getName();
        } catch (SQLException e) {
            return "";
        }
    }

    public void buyArtifactById(int user_id, int artifact_id){
        try {
            Student buyer = studentDAOI.getById(user_id);
            Artifact toBuy = artifactDAOI.getById(artifact_id);
            sackInventoryDAOI.insertSackInventory(new SackInventory(buyer.getId(), toBuy.getId(), true, toBuy.getPrice()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUniqueQuestCompleted(Student student){
        try {
            return studentDAOI.getQuestCompleted(student).keySet().size();
        } catch (SQLException e) {
            return 0;
        }
    }

}





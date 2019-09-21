package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.*;
import com.pixel.dao.postgresql.interfaces.SackInventoryDAO;
import com.pixel.model.Artifact;
import com.pixel.model.Quest;
import com.pixel.model.Student;

import java.sql.SQLException;

public class
MentorController {
    StudentDAOI studentDAOI ;
    ClassesDAOI classesDAOI;
    QuestDAOI questDAOI;
    ArtifactDAOI artifactDAOI;
    QuestCompletedDAOI questCompletedDAOI;
    SackInventoryDAOI sackInventoryDAOI;

    public MentorController(StudentDAOI studentDAOI, ClassesDAOI classesDAOI, QuestDAOI questDAOI, ArtifactDAOI artifactDAOI, QuestCompletedDAOI questCompletedDAOI, SackInventoryDAOI sackInventoryDAOI) {
        this.studentDAOI = studentDAOI;
        this.classesDAOI = classesDAOI;
        this.questDAOI = questDAOI;
        this.artifactDAOI = artifactDAOI;
        this.questCompletedDAOI = questCompletedDAOI;
        this.sackInventoryDAOI = sackInventoryDAOI;
    }

    public void createStudent(String name, String password, int mentor_id, int cass_id){
        try {
            studentDAOI.save(new Student(name, password, "student", mentor_id, cass_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createQuest(String name, String description, int exp, int categoryId){
        try {
            questDAOI.insertQuest(new Quest(name, description, exp, categoryId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createArtifact(String name, String description, int price, boolean is_global){
        try {
            artifactDAOI.insertArtifact(new Artifact(name, description, price, is_global));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuest(int id,String name, String description, int exp, int categoryId){
        try {
            questDAOI.updateQuest(new Quest(id, name, description, exp, categoryId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateArtifact(int id, String name, String description, int price, boolean is_global){
        try {
            artifactDAOI.updateArtifact(new Artifact(id, name, description, price, is_global));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void completeQuestByStudent(int studentId, int questId){
        try {
            questCompletedDAOI.completeQuest(studentDAOI.getById(studentId), questDAOI.getById(questId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disableArtifactsInSack(int sackItemId){
        sackInventoryDAOI.deactivateArtifact(sackItemId);
    }

}





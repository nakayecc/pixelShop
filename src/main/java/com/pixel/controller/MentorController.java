package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.*;
import com.pixel.model.Artifact;
import com.pixel.model.Mentor;
import com.pixel.model.Quest;
import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class
MentorController {
    MentorDAOI mentorDAOI;
    StudentDAOI studentDAOI ;
    ClassDAOI classDAOI;
    QuestDAOI questDAOI;
    ArtifactDAOI artifactDAOI;

    public MentorController(StudentDAOI studentDAOI, ClassDAOI classDAOI, QuestDAOI questDAOI, ArtifactDAOI artifactDAOI, MentorDAOI mentorDAOI) {
        this.studentDAOI = studentDAOI;
        this.classDAOI = classDAOI;
        this.questDAOI = questDAOI;
        this.artifactDAOI = artifactDAOI;
        this.mentorDAOI = mentorDAOI;
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

    public List<Mentor> getMentorList(){
        List<Mentor> mentorList = new ArrayList<>();

        try {
            mentorList = mentorDAOI.getListFull();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mentorList;
    }

}





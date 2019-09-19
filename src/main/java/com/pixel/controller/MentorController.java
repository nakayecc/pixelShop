package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.*;
import com.pixel.model.Artifact;
import com.pixel.model.Mentor;
import com.pixel.model.Quest;
import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class
MentorController {
    StudentDAOI studentDAOI ;
    ClassesDAOI classesDAOI;
    QuestDAOI questDAOI;
    ArtifactDAOI artifactDAOI;

    public MentorController(StudentDAOI studentDAOI, ClassesDAOI classesDAOI, QuestDAOI questDAOI, ArtifactDAOI artifactDAOI) {
        this.studentDAOI = studentDAOI;
        this.classesDAOI = classesDAOI;
        this.questDAOI = questDAOI;
        this.artifactDAOI = artifactDAOI;
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

}





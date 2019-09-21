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
CreepController {
    MentorDAOI mentorDAOI;
    StudentDAOI studentDAOI ;
    ClassesDAOI classesDAOI;
    QuestDAOI questDAOI;
    ArtifactDAOI artifactDAOI;
    QuestCompletedDAOI questCompletedDAOI;
    SackInventoryDAOI sackInventoryDAOI;

    public CreepController(StudentDAOI studentDAOI, ClassesDAOI classesDAOI, QuestDAOI questDAOI, ArtifactDAOI artifactDAOI, MentorDAOI mentorDAOI, QuestCompletedDAOI questCompletedDAOI, SackInventoryDAOI sackInventoryDAOI) {
        this.studentDAOI = studentDAOI;
        this.classesDAOI = classesDAOI;
        this.questDAOI = questDAOI;
        this.artifactDAOI = artifactDAOI;
        this.mentorDAOI = mentorDAOI;
        this.questCompletedDAOI = questCompletedDAOI;
        this.sackInventoryDAOI = sackInventoryDAOI;
    }

    public void createMentor(String name, String password, int cass_id){
        try {
            mentorDAOI.save(new Mentor(name, password, "mentor",  cass_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMentor(int id, String name, int cass_id){
        try {
            mentorDAOI.update(new Mentor(id, name, "mentor", cass_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createClass(String name){
        try {
            classesDAOI.createClass(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}





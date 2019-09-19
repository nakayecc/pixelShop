package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.ClassesDAOI;
import com.pixel.dao.postgresql.implementations.LevelsDAOI;
import com.pixel.dao.postgresql.implementations.QuestDAOI;
import com.pixel.dao.postgresql.implementations.StudentDAOI;
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

    public MentorController(StudentDAOI studentDAOI, ClassesDAOI classesDAOI, QuestDAOI questDAOI) {
        this.studentDAOI = studentDAOI;
        this.classesDAOI = classesDAOI;
        this.questDAOI = questDAOI;
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

}





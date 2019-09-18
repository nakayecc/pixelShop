package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.LevelsDAOI;
import com.pixel.dao.postgresql.implementations.StudentDAOI;
import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StudentController {
    StudentDAOI studentDAOI ;
    LevelsDAOI levelsDAOI;

    public StudentController(StudentDAOI studentDAOI, LevelsDAOI levelsDAOI) {
        this.studentDAOI = studentDAOI;
        this.levelsDAOI = levelsDAOI;
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

}





package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.ClassDAOI;
import com.pixel.model.StudentsClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassController {
    private ClassDAOI classDAOI;

    public ClassController(ClassDAOI classDAOI) {
        this.classDAOI = classDAOI;
    }

    public List<StudentsClass> getClassList(){
        List<StudentsClass> studentsClasses = new ArrayList<>();

        try {
            studentsClasses = classDAOI.getFullList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsClasses;
    }
}

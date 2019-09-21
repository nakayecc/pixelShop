package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.ClassesDAOI;
import com.pixel.model.StudentsClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassController {
    private ClassesDAOI classesDAOI;

    public ClassController(ClassesDAOI classesDAOI) {
        this.classesDAOI = classesDAOI;
    }

    public List<StudentsClass> getClassList(){
        List<StudentsClass> studentsClasses = new ArrayList<>();

        try {
            studentsClasses = classesDAOI.getFullList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsClasses;
    }
}

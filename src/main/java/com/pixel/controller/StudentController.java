package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.StudentDAOI;
import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentController {
    StudentDAOI studentDAOI = new StudentDAOI();





    public List<Student> getStudentList() throws SQLException {
        return studentDAOI.getListFull();
    }

    public Student getStudent(int id) throws SQLException {
        return studentDAOI.getById(id);
    }
}





package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    List<Student> getListFull() throws SQLException;
    List<Student> getListByValue(String value, String valueName) throws SQLException;
    Student getById(int value) throws SQLException;
    boolean save(Student s) throws SQLException;
    boolean update(Student s) throws SQLException;
    boolean delete(Student s) throws SQLException;
}

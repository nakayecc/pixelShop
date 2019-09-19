package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.StudentsClass;

import java.sql.SQLException;
import java.util.List;

public interface ClassDAO {
    StudentsClass getClassById(int id) throws SQLException;
    List<StudentsClass> getFullList() throws SQLException;
}

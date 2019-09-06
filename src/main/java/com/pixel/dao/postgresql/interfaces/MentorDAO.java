package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.Mentor;

import java.sql.SQLException;
import java.util.List;

public interface MentorDAO {
    List<Mentor> getListFull() throws SQLException;
    List<Mentor> getListByValue(String value, String valueName) throws SQLException;
    Mentor getById(int value) throws SQLException;
    boolean save(Mentor m) throws SQLException;
    boolean update(Mentor m) throws SQLException;
    boolean delete(Mentor m) throws SQLException;
}

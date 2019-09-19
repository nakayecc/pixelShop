package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.Artifact;
import com.pixel.model.Sack;

import java.sql.SQLException;
import java.util.List;

public interface SackDAO {
    Sack getById(int id) throws SQLException;
    List<Sack> getListFull() throws SQLException;
    List<Sack> getListBy(String valueName, String value) throws SQLException;
    List<Sack> getListBy(String valueName, int value) throws SQLException;
    boolean updateSack(Sack sack) throws SQLException;
    boolean insertSack(Sack sack) throws SQLException;
    boolean deleteSack(Sack sack) throws SQLException;
}

package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.Creep;

import java.sql.SQLException;
import java.util.List;

public interface CreepDAO {
    List<Creep> getListFull() throws SQLException;
    List<Creep> getListByValue(String value, String valueName) throws SQLException;
    Creep getById(int value) throws SQLException;
    boolean save(Creep c) throws SQLException;
    boolean update(Creep c) throws SQLException;
    boolean delete(Creep c) throws SQLException;
}

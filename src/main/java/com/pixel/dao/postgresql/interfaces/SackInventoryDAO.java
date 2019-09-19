package com.pixel.dao.postgresql.interfaces;

import com.pixel.model.Sack;
import com.pixel.model.SackInventory;

import java.sql.SQLException;
import java.util.List;

public interface SackInventoryDAO {
    SackInventory getById(int id) throws SQLException;
    List<SackInventory> getListFull() throws SQLException;
    List<SackInventory> getListBy(String valueName, String value) throws SQLException;
    public List<SackInventory> getListBy(String valueName, int value) throws SQLException;
    boolean updateSackInventory(SackInventory sackInventory) throws SQLException;
    boolean insertSackInventory(SackInventory sackInventory) throws SQLException;
    boolean deleteSackInventory(SackInventory sackInventory) throws SQLException;
}


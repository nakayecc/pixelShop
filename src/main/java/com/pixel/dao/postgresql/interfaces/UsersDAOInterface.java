package com.pixel.dao.postgresql.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface UsersDAOInterface<T> {
    List<T> getFullList() throws SQLException;
    T getByValue(String valueName, String value) throws SQLException;
    boolean save(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
}

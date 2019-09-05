package com.pixel.dao.postgresql.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface UsersDAO<T> {
    int getIdFromCredentials(String name, String password) throws SQLException;
    boolean checkIfUserIsCreep(int id) throws SQLException;
    boolean checkIfUserIsMentor(int id) throws SQLException;
    boolean checkIfUserIsStudent(int id) throws SQLException;

}

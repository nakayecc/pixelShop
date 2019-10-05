package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.UserDAOI;
import java.sql.SQLException;

public class UserController {
    private UserDAOI userDAOI;

    public UserController(UserDAOI userDAOI) {
        this.userDAOI = userDAOI;
    }

    public int getUserIdFromCredentials(String name, String password) throws SQLException {
        return userDAOI.getIdFromCredentials(name, password);
    }

    public boolean checkIfPasswordIsCorrect(String name, String password) throws SQLException {
        return getUserIdFromCredentials(name, password) > 1;
    }

    public String checkUserRank(int id) throws SQLException {


        if (userDAOI.checkIfUserIsCreep(id)) {
            return "creep";
        } else if (userDAOI.checkIfUserIsMentor(id)) {
            return "mentor";
        } else if (userDAOI.checkIfUserIsStudent(id)) {
            return "student";
        }
        return null;
    }
}

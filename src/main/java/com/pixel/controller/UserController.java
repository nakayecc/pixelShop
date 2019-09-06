package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.UserDAOI;

import java.sql.SQLException;

public class UserController {

    public UserController(UserDAOI dao) {
    }

    public int getUserIdFromCredentials(String name, String password) throws SQLException {
        return new UserDAOI().getIdFromCredentials(name, password);
        }

    public String checkUserRank(int id) throws SQLException {
        UserDAOI dao = new UserDAOI();

        if (dao.checkIfUserIsCreep(id)){
            return "creep";
        } else if (dao.checkIfUserIsMentor(id)){
            return "mentor";
        } else if (dao.checkIfUserIsStudent(id)){
            return "student";
        }
        return null;
    }
}

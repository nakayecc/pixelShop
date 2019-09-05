package com.pixel.controller;

import com.pixel.dao.postgresql.UserDAO;
import com.pixel.dao.postgresql.interfaces.UsersDAOInterface;

import java.sql.SQLException;

public class UserController {
    private UserDAO dao;

    public UserController(UserDAO dao) {
        this.dao = dao;
    }

    public int getUserIdFromCredentials(String name, String password) throws SQLException {
        return new UserDAO().getIdFromCredentials(name, password);
        }

    public String checkUserRank(int id) throws SQLException {

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

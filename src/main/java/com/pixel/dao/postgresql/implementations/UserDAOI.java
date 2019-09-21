package com.pixel.dao.postgresql.implementations;

import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.interfaces.UsersDAO;
import com.pixel.model.User;

import java.sql.*;

public class UserDAOI implements UsersDAO {
    private Connection connection;

    public UserDAOI(Connection connection) {
        this.connection = connection;
    }

    public void updateInUserTable(int id, String name, String password, String roleName) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "UPDATE users SET name = ?, password = ?, role_name = ? WHERE id = "+id+"";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, roleName);
        preparedStatement.executeUpdate();
    }

    public void updateInUserTableNoPassword(int id, String name, String roleName) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "UPDATE users SET name = ?, role_name = ? WHERE id = "+id+"";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, roleName);
        preparedStatement.executeUpdate();
    }

    public int getIdFromCredentials(String name, String password) throws SQLException {
        PreparedStatement preparedStatement;
        String query = "SELECT * FROM users WHERE name = ? AND password = ? LIMIT 1";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            return rs.getInt("id");
        }
        return -1;
    }

    public boolean checkIfUserIsCreep(int id) throws SQLException {
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT * from creeps WHERE user_id = "+id+"";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet.next();
    }

    public boolean checkIfUserIsMentor(int id) throws SQLException {
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT * from mentors WHERE user_id = "+id+"";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet.next();
    }

    public boolean checkIfUserIsStudent(int id) throws SQLException {
        Statement statement;
        ResultSet resultSet;
        String query = "SELECT * from students WHERE user_id = "+id+"";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet.next();
    }
    public void connClose() throws SQLException {
        connection.close();

    }
}

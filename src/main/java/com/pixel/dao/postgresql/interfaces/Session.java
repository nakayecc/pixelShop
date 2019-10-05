package com.pixel.dao.postgresql.interfaces;

import java.sql.SQLException;

public interface Session {

    void createSession(String session, int userId) throws SQLException;
    void deleteSessionById(int userId) throws SQLException;
    boolean isCurrentSession(String session) throws SQLException;
    int getUserId(String session) throws SQLException;
}

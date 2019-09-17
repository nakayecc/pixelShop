package com.pixel.dao.postgresql.interfaces;

public interface Session {

    void createSession(String session);
    void deleteSessionById(int userId);
    boolean isCurrentSession
}

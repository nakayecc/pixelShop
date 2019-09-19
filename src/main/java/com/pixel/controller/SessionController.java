package com.pixel.controller;

import com.pixel.dao.postgresql.implementations.SessionDAOI;
import com.pixel.view.CookieHandler;
import com.sun.net.httpserver.HttpExchange;

import java.sql.SQLException;


public class SessionController {
    private SessionDAOI sessionDAOI;
    private CookieHandler cookieHandler;

    public SessionController(SessionDAOI sessionDAOI, CookieHandler cookieHandler) {
        this.sessionDAOI = sessionDAOI;
        this.cookieHandler = cookieHandler;
    }

    public int getUserIdBySession(HttpExchange httpExchange) {
        String session = cookieHandler.extractCookieToString(cookieHandler.getCookie(httpExchange, "sessionId"));
        int userId = -1;
        try {
            userId = sessionDAOI.getUserId(session);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;

    }

    public void logout(HttpExchange httpExchange){
        String session = cookieHandler.extractCookieToString(cookieHandler.getCookie(httpExchange, "sessionId"));
        int userId = 0;
        try {
            userId = sessionDAOI.getUserId(session);
            sessionDAOI.deleteSessionById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

package com.pixel.view;

import com.pixel.controller.SessionController;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.SessionDAOI;
import com.pixel.helper.Common;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class Logout implements HttpHandler {



    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        Connection connection = postgreSQLJDBC.getConnection();


        SessionDAOI sessionDAOI = new SessionDAOI(connection);

        CookieHandler cookieHandler = new CookieHandler();
        SessionController sessionController = new SessionController(sessionDAOI, cookieHandler);

        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> cookie = cookieHandler.getCookie(httpExchange, "sessionId");
        HttpCookie cookies;
        String response = "";
        Common common = new Common();

        if (method.equals("GET")) {
            if (cookie.isPresent()) {
                try {
                    if (sessionDAOI.isCurrentSession(cookieHandler.extractCookieToString(cookie))) {
                        sessionController.logout(httpExchange);

                        httpExchange.getResponseHeaders().set("Location", "/login");
                        httpExchange.sendResponseHeaders(303, response.getBytes().length);

                    } else {
                        response = common.getLoginTemplate();
                        //httpExchange.sendResponseHeaders(303, response.getBytes().length);
                        //response = common.getLoginTemplate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                response = common.getLoginTemplate();
            }
            
            httpExchange.sendResponseHeaders(303, response.getBytes().length);
        }

    }
}

package com.pixel.view;

import com.pixel.dao.postgresql.implementations.SessionDAOI;
import com.pixel.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class Login implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        Student student = null; //find by cookie

        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/login.twig");
        JtwigModel model = JtwigModel.newModel();
        CookieHandler cookieHandler = new CookieHandler();
        HttpCookie httpCookie = new HttpCookie("SESSIONID", generateSessionID());
        cookieHandler.createCookie(httpExchange, httpCookie);

        response = template.render(model);
        //todo  login dao, przesylac do bazy session id, user id
        String method = httpExchange.getRequestMethod();
        sendResponse(httpExchange, response);
        //System.out.println(cookieHandler.getCookie(httpExchange,"SESSIONID"));
        Optional<HttpCookie> loginCookie = cookieHandler.getCookie(httpExchange, "SESSIONID");
        //System.out.println(loginCookie.get());
        String ciastko = cookieHandler.extractCookieToString(loginCookie);
        SessionDAOI sessionDAOI = new SessionDAOI();
//        try {
//            sessionDAOI.createSession(ciastko, 8);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            sessionDAOI.deleteSessionById(8);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            System.out.println(sessionDAOI.getUserId("f712a932-c971-48ca-9930-37e86caf7c8f"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private String generateSessionID() {
        UUID generatedId = UUID.randomUUID();
        return generatedId.toString();
    }



    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

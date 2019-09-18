package com.pixel.view;

import com.pixel.dao.postgresql.implementations.SessionDAOI;
import com.pixel.helper.Common;
import com.pixel.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Login implements HttpHandler {
    private SessionDAOI sessionDAOI;
    private Common common;

    public Login() {
        this.sessionDAOI = new SessionDAOI();
        this.common = new Common();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        CookieHandler cookieHandler = new CookieHandler();
        String response = "";
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> cookie = cookieHandler.getCookie(httpExchange, "sessionId");
        HttpCookie cookies;


        if (method.equals("GET")) {
            if (cookie.isPresent()) {
                try {
                    if (sessionDAOI.isCurrentSession(cookieHandler.extractCookieToString(cookie))){
                        httpExchange.getResponseHeaders().set("Location", "/");
                        httpExchange.sendResponseHeaders(303, response.getBytes().length);
                    } else {
                        response = common.getLoginTemplate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                response = common.getLoginTemplate();
            }
        }


        // If the form was submitted, retrieve it's content.
        if (method.equals("POST")) {
            //todo check user and password, create new cookie, insert it to db, redirect to


            if (cookie != null) {
                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();
                Map inputs = common.parseFormData(formData);
                //todo check password and username
                cookies = new HttpCookie("sessionId", generateSessionID());
                httpExchange.getResponseHeaders().add("Set-Cookie", cookies.toString());
                try {
                    sessionDAOI.createSession(cookies.getValue(), 8);
                    System.out.println("create session");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //response = loginUser(httpExchange);
                httpExchange.getResponseHeaders().set("Location", "/");
                //redirectToUserLandPage(httpExchange, userId);
                httpExchange.sendResponseHeaders(303, response.getBytes().length);
            } else {
                response = common.getLoginTemplate();
            }

        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private String generateSessionID() {
        UUID generatedId = UUID.randomUUID();
        return generatedId.toString();
    }





    private String loginUser(HttpExchange httpExchange) throws IOException {
        //todo get user id and identify
        String userType = "student";
        switch (userType) {
            case "creep":
                //todo
                break;
            case "mentor":
                //todo
                break;
            case "student":
                JtwigTemplate template = JtwigTemplate.classpathTemplate("template/index.twig");
                JtwigModel model = JtwigModel.newModel();
                String response = template.render(model);
                return response;
        }
        return "elo";
    }

}

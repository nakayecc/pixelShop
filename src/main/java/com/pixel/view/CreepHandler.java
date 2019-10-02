package com.pixel.view;

import com.pixel.controller.*;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
import com.pixel.helper.Common;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class CreepHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        Connection connection = postgreSQLJDBC.getConnection();

        ArtifactDAOI artifactDAOI = new ArtifactDAOI(connection);
        StudentDAOI studentDAOI = new StudentDAOI(connection);
        QuestDAOI questDAOI = new QuestDAOI(connection);
        QuestCompletedDAOI questCompletedDAOI = new QuestCompletedDAOI(connection);
        LevelsDAOI levelsDAOI = new LevelsDAOI(connection);
        ClassesDAOI classesDAOI = new ClassesDAOI(connection);
        SackInventoryDAOI sackInventoryDAOI = new SackInventoryDAOI(connection);
        MentorDAOI mentorDAOI = new MentorDAOI(connection);
        SessionDAOI sessionDAOI = new SessionDAOI(connection);


        ClassController classController = new ClassController(classesDAOI);
        CookieHandler cookieHandler = new CookieHandler();
        MentorController mentorController = new MentorController(studentDAOI, classesDAOI, questDAOI, artifactDAOI,
                mentorDAOI, questCompletedDAOI, sackInventoryDAOI);
        LvlController lvlController = new LvlController(levelsDAOI);
        Common common = new Common();

        handleRequest(httpExchange, connection, classController, mentorController, cookieHandler, sessionDAOI,
                lvlController);


        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void handleRequest(HttpExchange httpExchange, Connection connection,
                              ClassController classController, MentorController mentorController,
                              CookieHandler cookieHandler, SessionDAOI sessionDAOI, LvlController lvlController) {

        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/Creep.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentorList", mentorController.getMentorList());
        model.with("classList", classController.getClassList());
        model.with("classController", classController);
        model.with("lvlMap", lvlController.getAllLevel().entrySet());
        response = template.render(model);

        try {
            sendResponse(httpExchange, response);
            connection.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}

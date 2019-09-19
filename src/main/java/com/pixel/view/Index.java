package com.pixel.view;

import com.pixel.controller.*;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
import com.pixel.helper.Common;
import com.pixel.model.Quest;
import com.pixel.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class Index implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        Connection connection = postgreSQLJDBC.getConnection();

        SessionDAOI sessionDAOI = new SessionDAOI(connection);
        CookieHandler cookieHandler = new CookieHandler();
        Common common = new Common();



        String response = "";
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> cookie = cookieHandler.getCookie(httpExchange, "sessionId");

        if (method.equals("GET")) {
            if (cookie.isPresent()) {
                try {
                    if (sessionDAOI.isCurrentSession(cookieHandler.extractCookieToString(cookie))) {
                        handleRequest(httpExchange);

                    } else {
                        httpExchange.getResponseHeaders().set("Location", "/login");
                        httpExchange.sendResponseHeaders(303, response.getBytes().length);
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



    public void handleRequest(HttpExchange httpExchange) throws IOException {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

        Connection connection = postgreSQLJDBC.getConnection();

        ArtifactDAOI artifactDAOI = new ArtifactDAOI(connection);
        UserDAOI userDAOI = new UserDAOI(connection);
        StudentDAOI studentDAOI = new StudentDAOI(connection);
        QuestDAOI questDAOI = new QuestDAOI(connection);
        QuestCategoryDAOI questCategoryDAOI = new QuestCategoryDAOI(connection);
        LevelsDAOI levelsDAOI = new LevelsDAOI(connection);
        ClassesDAOI classesDAOI = new ClassesDAOI(connection);
        SackInventoryDAOI sackInventoryDAOI = new SackInventoryDAOI(connection);
        SackDAOI sackDAOI = new SackDAOI(connection);
        SessionDAOI sessionDAOI = new SessionDAOI(connection);
        CookieHandler cookieHandler = new CookieHandler();




        UserController userController = new UserController(userDAOI);
        StudentController studentController = new StudentController(studentDAOI,levelsDAOI, questDAOI, classesDAOI);
        QuestController questController = new QuestController(questDAOI);
        ArtifactController artifactController = new ArtifactController(artifactDAOI);
        OwnItemController ownItemController = new OwnItemController(sackDAOI,sackInventoryDAOI,artifactDAOI);
        SessionController sessionController = new SessionController(sessionDAOI, cookieHandler);

        String response = "";
        Student student = null; //find by cookie
        int userId = sessionController.getUserIdBySession(httpExchange);
        System.out.println(userId);
        try {

            student = studentController.getStudent(userId);

            System.out.println(student.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/Index.twig");
        JtwigModel model = JtwigModel.newModel();

        int index = 0;

        model.with("userName", student.getName());
        model.with("exp", studentController.getStudentExperience(student)); //TODO exp counting
        model.with("lvl", studentController.getUserLevel(student)); //TODO lvl
        model.with("indexQuest", index);
        model.with("QuestList", questController.getQuestList());
        model.with("artifactGroupList", artifactController.getGroupArtifact());
        model.with("artifactSoloList", artifactController.getSoloArtifact());
        model.with("studentArtifactList",ownItemController.getStudentOwnArtifact(student));
        model.with("questDoneMap",studentController.getAllQuestCompleted(student).entrySet());
        model.with("artifactShopSList",artifactController.getAllArtifact());

        response = template.render(model);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sendResponse(httpExchange, response);

    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    public static void main(String[] args) {
        String text = "ala ma kota";

        System.out.println(text.replaceAll(" ",""));
    }


}

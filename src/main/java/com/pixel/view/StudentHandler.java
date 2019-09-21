package com.pixel.view;

import com.pixel.controller.*;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
import com.pixel.helper.Common;
import com.pixel.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class StudentHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

        Connection connection = postgreSQLJDBC.getConnection();

        ArtifactDAOI artifactDAOI = new ArtifactDAOI(connection);
        UserDAOI userDAOI = new UserDAOI(connection);
        StudentDAOI studentDAOI = new StudentDAOI(connection);
        QuestDAOI questDAOI = new QuestDAOI(connection);
        LevelsDAOI levelsDAOI = new LevelsDAOI(connection);
        ClassesDAOI classesDAOI = new ClassesDAOI(connection);
        SackInventoryDAOI sackInventoryDAOI = new SackInventoryDAOI(connection);
        SackDAOI sackDAOI = new SackDAOI(connection);
        SessionDAOI sessionDAOI = new SessionDAOI(connection);
        QuestCategoryDAOI questCategoryDAOI = new QuestCategoryDAOI(connection);
        CookieHandler cookieHandler = new CookieHandler();


        UserController userController = new UserController(userDAOI);
        StudentController studentController = new StudentController(studentDAOI, levelsDAOI, questDAOI, classesDAOI, artifactDAOI, sackInventoryDAOI,questCategoryDAOI);
        QuestController questController = new QuestController(questDAOI,questCategoryDAOI);
        ArtifactController artifactController = new ArtifactController(artifactDAOI);
        OwnItemController ownItemController = new OwnItemController(sackInventoryDAOI, artifactDAOI);
        SessionController sessionController = new SessionController(sessionDAOI, cookieHandler);

        Common common = new Common();


        String response = "";
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> cookie = cookieHandler.getCookie(httpExchange, "sessionId");


        if (method.equals("GET")) {
            if (cookie.isPresent()) {
                try {
                    if (sessionDAOI.isCurrentSession(cookieHandler.extractCookieToString(cookie))) {
                        handleRequest(httpExchange, connection, cookieHandler, userController,
                                studentController, artifactController, ownItemController, sessionController, questController);
                    } else {
                        httpExchange.getResponseHeaders().set("Location", "/login");
                        httpExchange.sendResponseHeaders(303, response.getBytes().length);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                httpExchange.getResponseHeaders().set("Location", "/login");
                httpExchange.sendResponseHeaders(303, response.getBytes().length);
            }


        }

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = common.parseFormData(formData);
            String artifactId = String.valueOf(inputs.get("id"));
            studentController.buyArtifactById(sessionController.getUserIdBySession(httpExchange), Integer.valueOf(artifactId));
            httpExchange.getResponseHeaders().set("Location", "/");
            httpExchange.sendResponseHeaders(303, response.getBytes().length);

        }
        httpExchange.sendResponseHeaders(303, response.getBytes().length);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void handleRequest(HttpExchange httpExchange, Connection connection, CookieHandler cookieHandler, UserController userController,
                              StudentController studentController, ArtifactController artifactController,
                              OwnItemController ownItemController, SessionController sessionController,
                              QuestController questController) {


        String response = "";
        Student student = null; //find by cookie
        int userId = sessionController.getUserIdBySession(httpExchange);
        System.out.println(userId);
        try {

            student = studentController.getStudent(userId);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/Student.twig");
        JtwigModel model = JtwigModel.newModel();


        int index = 0;

        model.with("userName", student.getName());
        model.with("coin",studentController.getStudentMoney(student));
        model.with("level",studentController.getStudentExperience(student));
        model.with("exp", studentController.getStudentExperience(student)); //TODO exp counting
        model.with("lvl", studentController.getUserLevel(student)); //TODO lvl
        model.with("indexQuest", index);
        model.with("mentorName",studentController.getMentorName(student));
        model.with("activeBoost",ownItemController.getStudentOwnArtifact(student).size());
        model.with("doneQuest",studentController.getAllQuestCompleted(student).size());
        model.with("numberOfQuest",questController.getQuestList().size());
        model.with("className",studentController.getClassName(student));
        model.with("QuestList", questController.getQuestList());
        model.with("artifactGroupList", artifactController.getGroupArtifact());
        model.with("artifactSoloList", artifactController.getSoloArtifact());
        model.with("studentArtifactList", ownItemController.getStudentOwnArtifact(student));
        model.with("questDoneMap", studentController.getAllQuestCompleted(student).entrySet());
        model.with("percentageQuestCompleted", studentController.getPercentageOfCompleted(student));
        model.with("artifactShopSList", artifactController.getAllArtifact());
        System.out.println(ownItemController.getStudentOwnArtifact(student));
        response = template.render(model);
        try {
            sendResponse(httpExchange, response);
        } catch (IOException e) {
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

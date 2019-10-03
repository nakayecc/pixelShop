package com.pixel.view;

import com.pixel.controller.*;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
import com.pixel.helper.Common;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.HttpCookie;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Map;
import java.util.Optional;

public class CreepHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        Connection connection = postgreSQLJDBC.getConnection();

        ArtifactDAOI artifactDAOI = new ArtifactDAOI(connection);
        StudentDAOI studentDAOI = new StudentDAOI(connection);
        QuestDAOI questDAOI = new QuestDAOI(connection);
        QuestCompletedDAOI questCompletedDAOI = new QuestCompletedDAOI(connection);
        ClassesDAOI classesDAOI = new ClassesDAOI(connection);
        SackInventoryDAOI sackInventoryDAOI = new SackInventoryDAOI(connection);
        MentorDAOI mentorDAOI = new MentorDAOI(connection);
        SessionDAOI sessionDAOI = new SessionDAOI(connection);
        LevelsDAOI levelsDAOI = new LevelsDAOI(connection);


        ClassController classController = new ClassController(classesDAOI);
        CookieHandler cookieHandler = new CookieHandler();
        MentorController mentorController = new MentorController(studentDAOI, classesDAOI, questDAOI, artifactDAOI,
                mentorDAOI, questCompletedDAOI, sackInventoryDAOI);
        CreepController creepController = new CreepController(mentorDAOI, classesDAOI, levelsDAOI);
        Common common = new Common();

        String response = "";
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> cookie = cookieHandler.getCookie(httpExchange, "sessionId");

        if (method.equals("GET")) {
            if (cookie.isPresent()) {
                try {
                    if (sessionDAOI.isCurrentSession(cookieHandler.extractCookieToString(cookie))) {
                       handleRequest(httpExchange, connection, classController ,
                                mentorController, creepController);

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
        System.out.println(method);

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = common.parseFormData(formData);
            String formId = String.valueOf(inputs.get("id"));
            switch (formId) {
                case "addNewMentor": {
                    String mentorName = String.valueOf(inputs.get("mentorName"));
                    String password = String.valueOf(inputs.get("password"));
                    int classId = Integer.parseInt(inputs.get("classId").toString());
                    creepController.createMentor(mentorName, password, classId);
                    break;
                }
                case "editMentor": {
                    int mentorId = Integer.parseInt(inputs.get("mentorId").toString());
                    String mentorName = String.valueOf(inputs.get("mentorName"));
                    int classId = Integer.parseInt(inputs.get("classId").toString());
                    creepController.updateMentor(mentorId, mentorName, classId);
                    break;
                }
                case "addNewClass": {
                    String className = String.valueOf(inputs.get("className"));
                    creepController.createClass(className);
                    break;
                }
                case "editClass": {
                    String className = String.valueOf(inputs.get("className"));

                    break;
                }

                case "addLevel": {
                    String levelName = String.valueOf(inputs.get("levelName"));
                    int experience = Integer.parseInt(inputs.get("exp").toString());
                    creepController.addLevel(levelName, experience);
                    break;
                }

                case "deleteLevel": {
                    int levelId = Integer.parseInt(inputs.get("levelId").toString());
                    System.out.println(formData);
                    creepController.removeLevelByID(levelId);
                    break;
                }
            }

            httpExchange.getResponseHeaders().set("Location", "/creep");
            httpExchange.sendResponseHeaders(303, response.getBytes().length);
        }
        httpExchange.sendResponseHeaders(303, response.getBytes().length);


        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void handleRequest(HttpExchange httpExchange, Connection connection,
                              ClassController classController, MentorController mentorController,
                              CreepController creepController) {


        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/Creep.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentorList", mentorController.getMentorList());
        model.with("classList", classController.getClassList());
        model.with("classController", classController);
        model.with("lvlMap",creepController.getAllLevel().entrySet());
        model.with("lvlId",creepController);
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

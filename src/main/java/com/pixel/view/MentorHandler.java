package com.pixel.view;

import com.pixel.controller.*;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
import com.pixel.helper.Common;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class MentorHandler implements HttpHandler {
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
        QuestCategoryDAOI questCategoryDAOI = new QuestCategoryDAOI(connection);
        SessionDAOI sessionDAOI = new SessionDAOI(connection);


        StudentController studentController = new StudentController(studentDAOI, levelsDAOI, questDAOI, classesDAOI, artifactDAOI, sackInventoryDAOI,questCategoryDAOI);
        QuestController questController = new QuestController(questDAOI,questCategoryDAOI);
        ArtifactController artifactController = new ArtifactController(artifactDAOI);
        ClassController classController = new ClassController(classesDAOI);
        CookieHandler cookieHandler = new CookieHandler();
        MentorController mentorController = new MentorController(studentDAOI, classesDAOI,questDAOI,artifactDAOI,mentorDAOI, questCompletedDAOI, sackInventoryDAOI);
        Common common = new Common();

        String response = "";
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> cookie = cookieHandler.getCookie(httpExchange, "sessionId");

        if (method.equals("GET")) {
            if (cookie.isPresent()) {
                try {
                    if (sessionDAOI.isCurrentSession(cookieHandler.extractCookieToString(cookie))) {
                        handleRequest(httpExchange, connection,studentController, artifactController, questController, classController ,mentorController);

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
            System.out.println(method);


        }
        System.out.println(method);

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = common.parseFormData(formData);
            String formId = String.valueOf(inputs.get("id"));
            System.out.println(inputs);
            if(formId.equals("editUser")) {
                int userId = Integer.parseInt(inputs.get("userId").toString());
                String newUsername = String.valueOf(inputs.get("username"));
                int classId = Integer.parseInt(inputs.get("class").toString());
                int mentorId = Integer.parseInt(inputs.get("mentor").toString());
                System.out.println(inputs);
                mentorController.updateStudent(userId, newUsername, mentorId, classId);
            } else if(formId.equals("addUser")) {
                String username = String.valueOf(inputs.get("username"));
                String password = String.valueOf(inputs.get("password"));
                int classId = Integer.parseInt(inputs.get("class").toString());
                int mentorId = Integer.parseInt(inputs.get("mentor").toString());
                mentorController.createStudent(username, password, mentorId, classId);
            } else if(formId.equals("editQuest")) {
                int questId = Integer.parseInt(inputs.get("questId").toString());
                String questName = String.valueOf(inputs.get("questName"));
                String description = String.valueOf(inputs.get("description"));
                int exp = Integer.parseInt(inputs.get("exp").toString());
                int categoryId = Integer.parseInt(inputs.get("questCategory").toString());
                mentorController.updateQuest(questId, questName, description, exp, categoryId);
            } else if(formId.equals("addQuest")) {
                String questName = String.valueOf(inputs.get("questName"));
                String description = String.valueOf(inputs.get("description"));
                int exp = Integer.parseInt(inputs.get("exp").toString());
                int categoryId = Integer.parseInt(inputs.get("questCategory").toString());
                mentorController.createQuest(questName, description, exp, categoryId);
            } else if(formId.equals("editItem")) {
                int itemId = Integer.parseInt(inputs.get("itemId").toString());
                String itemName = String.valueOf(inputs.get("itemName"));
                String description = String.valueOf(inputs.get("description"));
                int price = Integer.parseInt(inputs.get("price").toString());
                boolean isGlobal = Boolean.getBoolean(inputs.get("isGlobal").toString());
                mentorController.updateArtifact(itemId, itemName, description, price, isGlobal);
            } else if(formId.equals("addItem")) {
                String itemName = String.valueOf(inputs.get("itemName"));
                String description = String.valueOf(inputs.get("description"));
                int price = Integer.parseInt(inputs.get("price").toString());
                boolean isGlobal = Boolean.getBoolean(inputs.get("isGlobal").toString());
                mentorController.createArtifact(itemName, description, price, isGlobal);
            }
            httpExchange.getResponseHeaders().set("Location", "/mentor");
            httpExchange.sendResponseHeaders(303, response.getBytes().length);

        }
        httpExchange.sendResponseHeaders(303, response.getBytes().length);
    }

    public void handleRequest(HttpExchange httpExchange, Connection connection,
                              StudentController studentController, ArtifactController artifactController,
                              QuestController questController,ClassController classController, MentorController mentorController) {

        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/Mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("studentList",studentController.getStudentList());
        model.with("studentController",studentController);
        model.with("classList",classController.getClassList());
        model.with("mentorList",mentorController.getMentorList());
        model.with("questList",questController.getQuestList());
        model.with("categoryList",questController.getQuestCategory());
        model.with("artifactList",artifactController.getAllArtifact());
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

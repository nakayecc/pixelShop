package com.pixel.view;

import com.pixel.controller.*;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
import com.pixel.helper.Common;
import com.pixel.model.Mentor;
import com.pixel.model.QuestCategory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MentorHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        Connection connection = postgreSQLJDBC.getConnection();

        ArtifactDAOI artifactDAOI = new ArtifactDAOI(connection);
        StudentDAOI studentDAOI = new StudentDAOI(connection);
        QuestDAOI questDAOI = new QuestDAOI(connection);
        LevelsDAOI levelsDAOI = new LevelsDAOI(connection);
        ClassDAOI classDAOI = new ClassDAOI(connection);
        SackInventoryDAOI sackInventoryDAOI = new SackInventoryDAOI(connection);
        MentorDAOI mentorDAOI = new MentorDAOI(connection);
        QuestCategoryDAOI questCategoryDAOI = new QuestCategoryDAOI(connection);

        StudentController studentController = new StudentController(studentDAOI, levelsDAOI, questDAOI, classDAOI, artifactDAOI, sackInventoryDAOI,questCategoryDAOI);
        QuestController questController = new QuestController(questDAOI,questCategoryDAOI);
        ArtifactController artifactController = new ArtifactController(artifactDAOI);
        ClassController classController = new ClassController(classDAOI);
        MentorController mentorController = new MentorController(studentDAOI,classDAOI,questDAOI,artifactDAOI,mentorDAOI);

        handleRequest(httpExchange, connection,studentController, artifactController, questController, classController ,mentorController);

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

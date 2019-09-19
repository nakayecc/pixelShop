package com.pixel.view;

import com.pixel.controller.ArtifactController;
import com.pixel.controller.OwnItemController;
import com.pixel.controller.QuestController;
import com.pixel.controller.StudentController;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.SessionDAOI;
import com.pixel.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class Index implements HttpHandler {
    private StudentController studentController;
    private QuestController questController;
    private ArtifactController artifactController;
    private OwnItemController ownItemController;

    public Index(
            StudentController studentController,
            QuestController questController,
            ArtifactController artifactController,
            OwnItemController ownItemController
    )
    {
        this.artifactController = artifactController;
        this.studentController = studentController;
        this.questController = questController;
        this.ownItemController = ownItemController;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        Connection connection = postgreSQLJDBC.getConnection();
        SessionDAOI sessionDAOI = new SessionDAOI(connection);
        CookieHandler cookieHandler = new CookieHandler();


        String response = "";
        Student student = null; //find by cookie
        try {
//            System.out.println(cookieHandler.extractCookieToString(cookieHandler.getCookie(httpExchange, "sessionId")));
            int userId = sessionDAOI.getUserId(cookieHandler.extractCookieToString(cookieHandler.getCookie(httpExchange, "sessionId")));
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

        response = template.render(model);
        sendResponse(httpExchange, response);

    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

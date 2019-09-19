package com.pixel.view;

import com.pixel.controller.*;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
import com.pixel.helper.Common;
import com.pixel.model.Mentor;
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
    public void handle(HttpExchange httpExchange) throws IOException {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

        Connection connection = postgreSQLJDBC.getConnection();

        ArtifactDAOI artifactDAOI = new ArtifactDAOI(connection);
        UserDAOI userDAOI = new UserDAOI(connection);
        StudentDAOI studentDAOI = new StudentDAOI(connection);
        QuestDAOI questDAOI = new QuestDAOI(connection);
        LevelsDAOI levelsDAOI = new LevelsDAOI(connection);
        ClassDAOI classDAOI = new ClassDAOI(connection);
        SackInventoryDAOI sackInventoryDAOI = new SackInventoryDAOI(connection);
        SessionDAOI sessionDAOI = new SessionDAOI(connection);
        MentorDAOI mentorDAOI = new MentorDAOI(connection);
        CookieHandler cookieHandler = new CookieHandler();

        UserController userController = new UserController(userDAOI);
        StudentController studentController = new StudentController(studentDAOI, levelsDAOI, questDAOI, classDAOI, artifactDAOI, sackInventoryDAOI);
        QuestController questController = new QuestController(questDAOI);
        ArtifactController artifactController = new ArtifactController(artifactDAOI);
        OwnItemController ownItemController = new OwnItemController(sackInventoryDAOI, artifactDAOI);
        SessionController sessionController = new SessionController(sessionDAOI, cookieHandler);
        ClassController classController = new ClassController(classDAOI);
        MentorController mentorController = new MentorController(studentDAOI,classDAOI,questDAOI,artifactDAOI,mentorDAOI);

        Common common = new Common();


        handleRequest(httpExchange, connection, cookieHandler, userController,
                studentController, artifactController, ownItemController, sessionController, questController, classController ,mentorController);

    }

    public void handleRequest(HttpExchange httpExchange, Connection connection, CookieHandler cookieHandler, UserController userController,
                              StudentController studentController, ArtifactController artifactController,
                              OwnItemController ownItemController, SessionController sessionController,
                              QuestController questController,ClassController classController, MentorController mentorController) {

        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/Mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("studentList",studentController.getStudentList());
        model.with("studentController",studentController);
        model.with("classList",classController.getClassList());
        model.with("mentorList",mentorController.getMentorList());
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

package com.pixel.view;

import com.pixel.controller.*;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
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


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
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



        UserController userController = new UserController(userDAOI);
        StudentController studentController = new StudentController(studentDAOI,levelsDAOI, questDAOI, classesDAOI);
        QuestController questController = new QuestController(questDAOI);
        ArtifactController artifactController = new ArtifactController(artifactDAOI);
        OwnItemController ownItemController = new OwnItemController(sackDAOI,sackInventoryDAOI,artifactDAOI);

        String response = "";
        Student student = null; //find by cookie
        try {
            student = studentController.getStudent(8);

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

}

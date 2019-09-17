package com.pixel.view;

import com.pixel.controller.ArtifactController;
import com.pixel.controller.QuestController;
import com.pixel.controller.StudentController;
import com.pixel.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class Index implements HttpHandler {
    private StudentController studentController;
    private QuestController questController;
    private ArtifactController artifactController;

    public Index(StudentController studentController, QuestController questController, ArtifactController artifactController) {
        this.artifactController = artifactController;
        this.studentController = studentController;
        this.questController = questController;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

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
        model.with("artefactGroupList",artifactController.getGroupArtifact());
        model.with("artifactPictureId",004);

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

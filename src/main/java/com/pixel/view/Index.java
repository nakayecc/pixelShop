package com.pixel.view;

import com.pixel.controller.StudentController;
import com.pixel.controller.UserController;
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

    public Index(StudentController studentController) {
        this.studentController = studentController;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        Student student = null; //find by cookie
        try {
            student = studentController.getStudent(7);

            System.out.println(student.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/Index.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("userName", student.getName());
        model.with("exp", 100); //TODO exp counting
        model.with("lvl", 10); //TODO lvl

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

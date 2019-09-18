package com.pixel;

import com.pixel.controller.ArtifactController;
import com.pixel.controller.QuestController;
import com.pixel.controller.StudentController;
import com.pixel.controller.UserController;
import com.pixel.dao.postgresql.PostgreSQLJDBC;
import com.pixel.dao.postgresql.implementations.*;
import com.pixel.view.Index;
import com.pixel.view.Static;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

        ArtifactDAOI artifactDAOI = new ArtifactDAOI(postgreSQLJDBC.getConnection());
        UserDAOI userDAOI = new UserDAOI(postgreSQLJDBC.getConnection());
        StudentDAOI studentDAOI = new StudentDAOI(postgreSQLJDBC.getConnection());
        QuestDAOI questDAOI = new QuestDAOI(postgreSQLJDBC.getConnection());
        QuestCategoryDAOI questCategoryDAOI = new QuestCategoryDAOI(postgreSQLJDBC.getConnection());
        LevelsDAOI levelsDAOI = new LevelsDAOI(postgreSQLJDBC.getConnection());


        UserController userController = new UserController(userDAOI);
        StudentController studentController = new StudentController(studentDAOI,levelsDAOI, questDAOI);
        QuestController questController = new QuestController(questDAOI);
        ArtifactController artifactController = new ArtifactController(artifactDAOI);

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", new Index(studentController, questController,artifactController));
            server.createContext("/static", new Static());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

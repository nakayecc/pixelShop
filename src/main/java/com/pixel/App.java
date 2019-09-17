package com.pixel;

import com.pixel.controller.QuestController;
import com.pixel.controller.StudentController;
import com.pixel.controller.UserController;
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
        UserController userController = new UserController();
        StudentController studentController = new StudentController();
        QuestController questController = new QuestController();

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", new Index(studentController, questController));
            server.createContext("/static", new Static());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

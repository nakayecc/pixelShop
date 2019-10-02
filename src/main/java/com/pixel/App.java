package com.pixel;


import com.pixel.model.Creep;
import com.pixel.view.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {


        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/", new StudentHandler());
            server.createContext("/mentor", new MentorHandler());
            server.createContext("/static", new Static());
            server.createContext("/login", new Login());
            server.createContext("/logout", new Logout());
            server.createContext("/creep", new CreepHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

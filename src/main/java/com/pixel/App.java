package com.pixel;


import com.pixel.view.Index;
import com.pixel.view.Login;
import com.pixel.view.Logout;
import com.pixel.view.Static;
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
            server.createContext("/", new Index());
            server.createContext("/static", new Static());
            server.createContext("/login", new Login());
            server.createContext("/logout", new Logout());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

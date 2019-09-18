package com.pixel.view;

import com.pixel.dao.postgresql.implementations.SessionDAOI;
import com.pixel.model.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Login implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();


        // Send a form if it wasn't submitted yet.
        if(method.equals("GET")){
            response = getLoginTemplate();
        }

        // If the form was submitted, retrieve it's content.
        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            //System.out.println(formData);
            Map inputs = parseFormData(formData);

            JtwigTemplate template = JtwigTemplate.classpathTemplate("template/index.twig");
            JtwigModel model = JtwigModel.newModel();

            httpExchange.getResponseHeaders().set("Location", "/");
            httpExchange.sendResponseHeaders(303, -1);
            //redirectToUserLandPage(httpExchange, userId);
            httpExchange.sendResponseHeaders(303, response.getBytes().length);

        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }





    private String generateSessionID() {
        UUID generatedId = UUID.randomUUID();
        return generatedId.toString();
    }
    private String getLoginTemplate() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/login.twig");
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);
        return response;
    }
    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
    private String loginUser(HttpExchange httpExchange) throws IOException {
        //todo get user id and identify
        String userType = "student";
        switch (userType) {
            case "creep":
                //todo
                break;
            case "mentor":
                //todo
                break;
            case "student":
                JtwigTemplate template = JtwigTemplate.classpathTemplate("template/index.twig");
                JtwigModel model = JtwigModel.newModel();
                String response = template.render(model);
                return response;
        }
        return "elo";
    }


    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

//package com.pixel.view;
//
//import com.codecool.dao.ILoginDao;
//import com.codecool.dao.ISessionDao;
//import com.codecool.hasher.PasswordHasher;
//import com.codecool.model.User;
//import com.codecool.server.helper.CommonHelper;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import org.jtwig.JtwigModel;
//import org.jtwig.JtwigTemplate;
//
//import java.io.*;
//import java.net.HttpCookie;
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class Test implements HttpHandler {
//    private ISessionDao sessionDao;
//    private ILoginDao loginDao;
//    private CommonHelper commonHelper;
//
//    public Test(ISessionDao sessionDao, ILoginDao loginDao, CommonHelper commonHelper) {
//        this.sessionDao = sessionDao;
//        this.loginDao = loginDao;
//        this.commonHelper = commonHelper;
//    }
//
//    @Override
//    public void handle(HttpExchange httpExchange) throws IOException {
//        String response = "";
//        String method = httpExchange.getRequestMethod();
//        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
//        HttpCookie cookie;
//        if (method.equals("GET")) {
//            if (cookieStr != null) {
//                cookie = HttpCookie.parse(cookieStr).get(0);
//                if (sessionDao.isCurrentSession(cookie.getValue())) {
//                    int userId = sessionDao.getUserIdBySessionId(cookie.getValue());
//                    redirectToUserLandPage(httpExchange, userId);
//                } else {
//                    response = getLoginFrom();
//                    httpExchange.sendResponseHeaders(200, response.length());
//                }
//            } else {
//                response = getLoginFrom();
//                httpExchange.sendResponseHeaders(200, response.getBytes().length);
//            }
//        }
//
//
//        if (method.equals("POST")) {
//            InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String formData = bufferedReader.readLine();
//
//            Map<String, String> inputs = commonHelper.parseFormData(formData);
//            String login = inputs.get("login");
//            String password = inputs.get("password");
//            if (loginDao.checkIfLoginIsCorrect(login)) {
//                String salt = loginDao.getSaltByLogin(login);
//                PasswordHasher passwordHasher = new PasswordHasher();
//                String hashedPassword = passwordHasher.hashPassword(salt + password);
//                if (loginDao.checkIfPasswordIsCorrect(login, hashedPassword)) {
//                    int userId = loginDao.getUserId(login);
//                    UUID uuid = UUID.randomUUID();
//                    cookie = new HttpCookie("sessionId", String.valueOf(uuid));
//                    httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
//                    sessionDao.insertSessionId(uuid.toString(), userId);
//
//                    redirectToUserLandPage(httpExchange, userId);
//                    httpExchange.sendResponseHeaders(303, response.getBytes().length);
//
//                } else {
//                    response = getLoginFrom();
//                    httpExchange.sendResponseHeaders(200, response.getBytes().length);
//                }
//            } else {
//                response = getLoginFrom();
//                httpExchange.sendResponseHeaders(200, response.getBytes().length);
//            }
//        }
//        commonHelper.sendResponse(httpExchange, response);
//    }
//
//    private void redirectToUserLandPage(HttpExchange httpExchange, int userId) throws IOException {
//        String userType = loginDao.getUserById(userId).getType();
//        switch (userType) {
//            case "admin":
//                commonHelper.redirectToUserPage(httpExchange, "/admin");
//                break;
//            case "mentor":
//                commonHelper.redirectToUserPage(httpExchange, "/mentor");
//                break;
//            case "student":
//                commonHelper.redirectToUserPage(httpExchange, "/student");
//                break;
//        }
//    }
//
//
//    private String getLoginFrom() {
//        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/index.twig");
//        JtwigModel model = JtwigModel.newModel();
//        String response = template.render(model);
//        return response;
//    }
//}

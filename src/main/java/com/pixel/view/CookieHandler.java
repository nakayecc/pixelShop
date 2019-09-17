package com.pixel.view;

import com.pixel.helper.CookieHelper;
import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.List;
import java.util.Optional;

public class CookieHandler {
    private static final String SESSION_COOKIE_NAME = "sessionID";
    private static final String IS_LOGGED_COOKIE_NAME = "isLogged";
    private static final String USER_COOKIE_NAME = "lol";

    private CookieHelper cookieHelper = new CookieHelper();


    public void createCookie(HttpExchange httpExchange, HttpCookie cookie){
          httpExchange.getResponseHeaders().add("Set-Cookie", String.valueOf(cookie));
    }

    public String readCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        return cookieStr;
    }

    Optional<HttpCookie> getCookie(HttpExchange httpExchange, String nameCookie) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookieList = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(nameCookie,cookieList);
    }

    public String extractCookieToString(Optional<HttpCookie> cookie){
        String value = "";
        if(cookie.isPresent()){
            HttpCookie wrapCookie = cookie.get();
            value = wrapCookie.getValue();
            value = value.substring(1,value.length()-1);
        ;

        }
        return value;
    }

}

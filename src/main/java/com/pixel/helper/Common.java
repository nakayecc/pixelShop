package com.pixel.helper;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Common {

    public Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    public String getLoginTemplate() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("template/login.twig");
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);
        return response;
    }
}

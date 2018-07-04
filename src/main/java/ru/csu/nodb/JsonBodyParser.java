package ru.csu.nodb;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonBodyParser {

    public void parseJsonBody(String body) {
        jsonObject = parser.parse(body).getAsJsonObject();
    }

    public String getValue(String key) {
        return jsonObject.get(key).getAsString();
    }

    private JsonParser parser = new JsonParser();
    private JsonObject jsonObject;
}

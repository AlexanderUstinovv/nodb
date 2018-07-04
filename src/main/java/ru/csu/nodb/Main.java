package ru.csu.nodb;

import com.google.gson.Gson;
import ru.csu.nodb.data.DataStorageImpl;
import ru.csu.nodb.prop.PropertiesLoaderImpl;
import ru.csu.nodb.prop.PropertiesStorage;

import java.io.IOException;
import java.net.URISyntaxException;

import static spark.Spark.*;

public class Main {

    private static void init() throws IOException, URISyntaxException {
        try {
            PropertiesStorage propertiesStorage =
                    new PropertiesLoaderImpl().loadProperties("./config.properties");
            port(propertiesStorage.getPort());
            dataStorageImpl = DataStorageImpl.getInstance();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        init();

        path("/api", () -> {
            get("/read/:id", (request, response) -> {
                try {
                    return gson.toJson(dataStorageImpl.read(Integer.parseInt(request.params("id"))));
                } catch (NumberFormatException ex) {
                    response.status(404);
                }
                return null;
            });

            post("/write", (request, response) -> {
                jsonBodyParser.parseJsonBody(request.body());
                dataStorageImpl.write(jsonBodyParser.getValue("value"));
                return gson.toJson(true);
            });
        });
    }

    private static DataStorageImpl dataStorageImpl;
    private static Gson gson = new Gson();
    private static JsonBodyParser jsonBodyParser = new JsonBodyParser();
}

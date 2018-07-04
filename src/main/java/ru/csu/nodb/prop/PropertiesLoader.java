package ru.csu.nodb.prop;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PropertiesLoader {
    PropertiesStorage loadProperties(String propertiesPath) throws IOException, URISyntaxException;
}

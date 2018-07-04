package ru.csu.nodb.prop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class PropertiesLoaderImpl implements PropertiesLoader {

    @Override
    public PropertiesStorage loadProperties(String propertiesPath) throws IOException, URISyntaxException {

        try(FileInputStream fileInputStream = new FileInputStream(propertiesPath)) {
            Properties property = new Properties();
            property.load(fileInputStream);

            PropertiesStorage storage = PropertiesStorage.getInstance();
            String propetyMaster = property.getProperty("db.master");

            boolean isMaster =  propetyMaster.equals("true") || propetyMaster.equals("false") ?
                    Boolean.valueOf(propetyMaster) : false;

            storage.setMaster(isMaster);

            if (isMaster) {
                storage.setFirstSlaveUrl(new URI(property.getProperty("db.firstSlaveUrl")));
                storage.setSecondSlaveUrl(new URI(property.getProperty("db.secondSlaveUrl")));
            }

            storage.setAsyncMode(Boolean.parseBoolean(property.getProperty("db.asyncMode")));

            storage.setPort(Integer.parseInt(property.getProperty("db.port")));
            return storage;

        } catch (IOException e) {
            logger.error("Error while loading properties file", e);
            throw e;
        } catch (URISyntaxException e) {
            logger.error("Bad slave urls properties", e);
            throw e;
        }
    }

    private Logger logger = LoggerFactory.getLogger(PropertiesLoaderImpl.class);
}

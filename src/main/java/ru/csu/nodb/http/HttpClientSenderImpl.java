package ru.csu.nodb.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class HttpClientSenderImpl implements HttpClientSender {

    public void sendPost(HttpPost httpPost, String jsonString) throws Exception {
        try {
            StringEntity input = new StringEntity(jsonString);
            input.setContentType("application/json");
            httpPost.setEntity(input);
            HttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("Failed : HTTP error code : " +
                        String.valueOf(response.getStatusLine().getStatusCode())
                );
            } else {
                logger.info(httpPost.getURI().toString() + " returned status 200");
            }
        } catch (Exception e) {
            logger.error("Error while sending data " + jsonString + " " + new Date());
            throw e;
        }
    }

    private Logger logger = LoggerFactory.getLogger(HttpClientSenderImpl.class);
    private HttpClient client = HttpClientBuilder.create().build();
}

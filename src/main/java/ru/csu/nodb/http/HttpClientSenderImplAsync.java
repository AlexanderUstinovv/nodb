package ru.csu.nodb.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Future;

public class HttpClientSenderImplAsync implements HttpClientSender {

    @Override
    public void sendPost(HttpPost httpPost, String jsonString) throws Exception {

        try(CloseableHttpAsyncClient asyncHttpClient = HttpAsyncClients.createDefault()) {
            asyncHttpClient.start();
            StringEntity input = new StringEntity(jsonString);
            input.setContentType("application/json");
            httpPost.setEntity(input);
            Future<HttpResponse> future = asyncHttpClient.execute(httpPost, null);
            HttpResponse response = future.get();
            logger.info(
                    "Async response: httpPost.getURI().toString() + " + response.getStatusLine() + " " + new Date()
            );
            logger.info("Async shutting down " + new Date());
        }
    }

    private Logger logger = LoggerFactory.getLogger(HttpClientSenderImplAsync.class);
}

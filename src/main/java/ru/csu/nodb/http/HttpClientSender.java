package ru.csu.nodb.http;

import org.apache.http.client.methods.HttpPost;

public interface HttpClientSender {
    void sendPost(HttpPost httpPost, String jsonString) throws Exception;
}

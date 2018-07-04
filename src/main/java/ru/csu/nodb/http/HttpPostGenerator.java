package ru.csu.nodb.http;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;

public interface HttpPostGenerator {
    HttpPost[] generate(URI...args);
}

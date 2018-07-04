package ru.csu.nodb.http;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpPostGeneratorImpl implements HttpPostGenerator {
    @Override
    public HttpPost[] generate(URI... args) {

        List<HttpPost> generated = new ArrayList<>();

        for (URI uri : args) {
            generated.add(new HttpPost(uri));
        }

        return Arrays.copyOf(generated.toArray(), generated.size(), HttpPost[].class);
    }
}

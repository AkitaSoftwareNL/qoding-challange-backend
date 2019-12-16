package nl.quintor.qodingchallenge.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpRequestUtils {

    public ResponseEntity<?> get(String url, Class<?> resultClass) {
        RestTemplate template = new RestTemplate();
        return template.getForEntity(url, resultClass);
    }

    public ResponseEntity<?> post(String url, Object objectToSend, Class<?> resultClass) {
        RestTemplate template = new RestTemplate();
        return template.postForEntity(url, objectToSend, resultClass);
    }
}

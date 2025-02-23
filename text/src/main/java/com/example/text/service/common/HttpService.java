package com.example.text.service.common;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HttpService {

    @Autowired
    private RestTemplate restTemplate;

    public JSONObject sendRequest(String url, HttpMethod method, Map<String, String> headers, Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers!= null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.set(entry.getKey(), entry.getValue());
            }
        }

        HttpEntity<Object> requestEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, method, requestEntity, JSONObject.class);
        return response.getBody();
    }
}

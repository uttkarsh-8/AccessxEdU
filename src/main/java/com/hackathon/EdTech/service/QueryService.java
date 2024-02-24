package com.hackathon.EdTech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryService {

    @Autowired
    private RestTemplate restTemplate;

    public String queryFlask(String query) {
        String url = "http://localhost:5000/query";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> request = new HashMap<>();
        request.put("query", query);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(url, entity, String.class);
    }
}

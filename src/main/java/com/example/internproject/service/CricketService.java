package com.example.internproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CricketService {

    @Value("${cricket.api.key}")
    private String apiKey;

    @Value("${cricket.api.endpoint}")
    private String apiUrl;
    
    @Autowired
    private RestTemplate restTemplate;


    public String getLiveScores() {
        String url = apiUrl + "?apikey=" + apiKey;
        return restTemplate.getForObject(url, String.class);
    }
}

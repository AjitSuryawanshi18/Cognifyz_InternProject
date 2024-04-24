//this is for cricket api implementation
package com.example.internproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CricketConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


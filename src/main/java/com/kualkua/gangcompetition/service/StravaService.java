package com.kualkua.gangcompetition.service;

import com.kualkua.gangcompetition.client.impl.StravaClientImpl;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class StravaService {

    private static final Logger log = LoggerFactory.getLogger(StravaService.class);

    @Autowired
    StravaClientImpl stravaClient;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            JSONObject obj = new RestTemplateBuilder().build().getForObject(
                    "https://jsonplaceholder.typicode.com/users/1", JSONObject.class);
            String jsonResponse = obj != null ? obj.toJSONString() : "undefined";
            log.info(jsonResponse);
        };
    }
}

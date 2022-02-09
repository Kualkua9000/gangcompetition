package com.kualkua.gangcompetition.client.impl;

import com.kualkua.gangcompetition.client.StravaClient;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StravaClientImpl implements StravaClient {

    private static final Logger log = LoggerFactory.getLogger(StravaClientImpl.class);

    @Value("${testUrl}")
    String testUrl;

    @Value("${stravaRetardsClubId}")
    String stravaRetardsClubId;

    @Override
    @Bean
    public CommandLineRunner connect() {
        return args -> {
            JSONObject json = new RestTemplateBuilder()
                    .build()
                    .getForObject(testUrl, JSONObject.class);
            String jsonResponse = json != null ? json.toJSONString() : "undefined";
            log.info(jsonResponse);
        };
    }
}

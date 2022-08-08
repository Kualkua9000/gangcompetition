package com.kualkua.gangcompetition.client.impl;

import com.kualkua.gangcompetition.client.StravaClient;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

@Component
public class StravaClientImpl implements StravaClient {

    private static final Logger log = LoggerFactory.getLogger(StravaClientImpl.class);

    @Value("${stravaTestUrl}")
    String testUrl;

    @Value("${stravaRetardsClubId}")
    String stravaRetardsClubId;

    @Override
    public String getAuthString() {
        // TODO: 10.02.2022
        return null;
    }

    @Override
    public String getBearer(String authString) {
        return null;
    }

    @Override
    public JSONObject getUserInfo(String jwt) {
        JSONObject json = new RestTemplateBuilder()
                .build()
                .getForObject(testUrl, JSONObject.class);
        String jsonResponse = json != null ? json.toJSONString() : "undefined";
        log.info(jsonResponse);
        return json;
    }

    @Override
    public JSONObject getLastActivity() {
            JSONObject json = new RestTemplateBuilder()
                    .defaultHeader("Authorization", "Bearer *")
                    .build()
                    .getForObject("https://www.strava.com/api/v3/athletes/21288485/stats", JSONObject.class);
            String jsonResponse = json != null ? json.toJSONString() : "undefined";
            log.info(jsonResponse);
            //id=21288485
            return json;
    }
}

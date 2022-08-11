package com.kualkua.gangcompetition.client.impl;

import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.repository.ActivityRepository;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Component
public class StravaClientImpl implements StravaClient {

    @Value("${clientId}")
    String clientId;

    @Value("${clientSecret}")
    String clientSecret;

    @Value("${stravaTokenUrl}")
    String stravaTokenUrl;

    private static final Logger log = LoggerFactory.getLogger(StravaClientImpl.class);

    public final ActivityRepository activityRepository;

    @Value("${stravaTestUrl}")
    String testUrl;

    @Value("${stravaRetardsClubId}")
    String stravaRetardsClubId;

    public StravaClientImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @SneakyThrows
    @Override
    public String getAuthString() {
        return Objects.requireNonNull(Objects.requireNonNull(new RestTemplateBuilder()
                .build()
                .postForLocation("http://www.strava.com/oauth/authorize?client_id=" + clientId +
                                "&response_type=code&redirect_uri=http://localhost:8080/exchange_token&approval_prompt=force&scope=read",
                        ResponseEntity.class)).toString());
    }

    @SneakyThrows
    @Override
    public String getBearer(String authCode) {

        URI uri = UriComponentsBuilder.fromUriString(stravaTokenUrl)
                .queryParam("code", authCode)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", "authorization_code")
                .build()
                .toUri();

        JSONObject json = new RestTemplateBuilder()
                .build()
                .postForObject(uri,
                        null,
                        JSONObject.class);
        String jsonResponse = json != null ? json.toJSONString() : "undefined";
        log.info(jsonResponse);
        return Objects.requireNonNull(json).getAsString("access_token");
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
                .defaultHeader("Authorization", "")
                .build()
                .getForObject("https://www.strava.com/api/v3/athletes/21288485/stats", JSONObject.class);
        String jsonResponse = json != null ? json.toJSONString() : "undefined";
        log.info(jsonResponse);
        return new JSONObject();
    }


}

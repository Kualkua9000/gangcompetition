package com.kualkua.gangcompetition.client;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public interface StravaClient {

        String getAuthString();
        String getBearer(String authCode);
        JSONObject getLastActivity();
        JSONObject getUserInfo(String jwt);
}

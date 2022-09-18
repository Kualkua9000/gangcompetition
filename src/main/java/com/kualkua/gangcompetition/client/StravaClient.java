package com.kualkua.gangcompetition.client;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public interface StravaClient {

        String getAuthString();
        OAuthToken getBearer(String authCode);
        OAuthToken updateBearer(String refreshToken);
        JSONArray getActivities();
        JSONObject getLastActivity();
        JSONObject getUserInfo(String jwt);
        void saveMemberRefresh(String token);
}

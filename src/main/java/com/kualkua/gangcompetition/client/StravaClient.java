package com.kualkua.gangcompetition.client;

import com.kualkua.gangcompetition.domain.strava.ActivityStravaModel;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public interface StravaClient {

        String getAuthString();
        OAuthToken getBearer(String authCode);
        OAuthToken updateBearer(String refreshToken);
        ActivityStravaModel[] getActivities();
        JSONObject getLastActivity();
        JSONObject getUserInfo(String jwt);
        void saveMemberRefresh(String token);
}

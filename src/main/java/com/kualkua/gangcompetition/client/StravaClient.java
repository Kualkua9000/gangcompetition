package com.kualkua.gangcompetition.client;

import com.kualkua.gangcompetition.domain.strava.StravaActivityModel;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public interface StravaClient {

        String getAuthString();
        OAuthToken getBearer(String authCode);
        OAuthToken updateBearer(String refreshToken);
        StravaActivityModel[] getActivities();
        JSONObject getLastActivity();
        JSONObject getUserInfo(String jwt);
        void saveMemberRefresh(String token);
}

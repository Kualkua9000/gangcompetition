package com.kualkua.gangcompetition.client;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public interface StravaClient {

        String getAuthString();
        JSONObject getLastActivity();
}

package com.kualkua.gangcompetition.service;

import com.kualkua.gangcompetition.client.impl.StravaClientImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StravaService {

    @Autowired
    StravaClientImpl client;

    public JSONObject getUserLastActivity() {
        client.getAuthString();
        return client.getLastActivity();
    }
}

package com.kualkua.gangcompetition;

import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.client.impl.StravaClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GangcompetitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(GangcompetitionApplication.class, args);
        StravaClient stravaClient = new StravaClientImpl();
        stravaClient.getLastActivity();
    }
}

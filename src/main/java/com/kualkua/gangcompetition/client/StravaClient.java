package com.kualkua.gangcompetition.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/")
public interface StravaClient {
        CommandLineRunner connect();
}

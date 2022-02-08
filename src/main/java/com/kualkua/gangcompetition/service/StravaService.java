package com.kualkua.gangcompetition.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class StravaService {

    private static final Logger log = LoggerFactory.getLogger(StravaService.class);

    @Bean
    public CommandLineRunner run() {
        return args -> {
            Object obj = new RestTemplateBuilder().build().getForObject(
                    "https://jsonplaceholder.typicode.com/todos/1", Object.class);
            log.info(obj != null ? obj.toString() : "error");
        };
    }
}

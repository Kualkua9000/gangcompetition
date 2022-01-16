package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.domain.Activity;
import com.kualkua.gangcompetition.domain.ActivityType;
import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ActivityRepository repository;

    @GetMapping("/")
    public String hello(Map<String, Object> model) {
        return "welcome";
    }

    @GetMapping("/main")
    public String addNewUser(Map<String, Object> model) {
        return "main";
    }

    @PostMapping("/main")
    public String addNewActivity(
            @AuthenticationPrincipal Member member,
            @RequestParam String activityType,
            @RequestParam double activityDistance,
            @RequestParam Date activityDate,
            @RequestParam String activityTime,
            @RequestParam String activityPace,
            Map<String, Object> model) {
        Activity activity = new Activity(
                activityDistance,
                activityTime,
                activityPace,
                activityDate,
                Collections.singleton(ActivityType.valueOf(activityType.toUpperCase())));
        repository.save(activity);
        List<Activity> activityList = new ArrayList<>(repository.findAll());
        model.put("members", activityList);
        return "main";
    }
}

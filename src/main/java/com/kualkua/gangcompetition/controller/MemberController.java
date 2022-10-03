package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.repository.MemberRepository;
import net.minidev.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Controller
@RequestMapping("/user")
public class MemberController {

    final MemberRepository memberRepository;
    final StravaClient stravaClient;

    public MemberController(MemberRepository memberRepository, StravaClient stravaClient) {
        this.memberRepository = memberRepository;
        this.stravaClient = stravaClient;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", memberRepository.findAll());
        return "userList";
    }

    @GetMapping("/stravaAuth")
    public String userStravaAuthentication(RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return "redirect:" + (stravaClient.getAuthString());
    }

    @GetMapping("/activities")
    public String getActivities(Model model) {
        //stravaClient.getActivities();
        //LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap) stravaClient.getActivities();
        model.addAttribute("activityList", stravaClient.getActivities().toJSONString());
        return "activityList";
    }
}

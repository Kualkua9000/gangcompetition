package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.service.MemberService;
import com.kualkua.gangcompetition.service.StravaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {

    final StravaService stravaService;
    final MemberService memberService;

    public RegistrationController(StravaService stravaService, MemberService memberService) {
        this.stravaService = stravaService;
        this.memberService = memberService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Member user, Map<String, Object> model) {
        Member memberFromDb = memberService.registrationUser(user);
        if (memberFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/exchange_token")
    public String getBearer(@RequestParam(name = "code") String code) {
        stravaService.initializeToken(code);
        return "redirect:/stravaAuth";
    }
}

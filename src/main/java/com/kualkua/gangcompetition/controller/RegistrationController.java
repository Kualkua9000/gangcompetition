package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.domain.Role;
import com.kualkua.gangcompetition.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    final MemberRepository memberRepository;
    final StravaClient stravaClient;

    public RegistrationController(MemberRepository memberRepository, StravaClient stravaClient) {
        this.memberRepository = memberRepository;
        this.stravaClient = stravaClient;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Member user, Map<String, Object> model) {
        Member memberFromDb = memberRepository.findByUsername(user.getUsername());

        if (memberFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        memberRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/exchange_token")
    public String getBearer(@RequestParam(name = "code") String code) {
        String jwt = stravaClient.getBearer(code);
        System.out.println("___authCode: " + code);
        System.out.println("___jwt: " + jwt);
        return "main";
    }
}

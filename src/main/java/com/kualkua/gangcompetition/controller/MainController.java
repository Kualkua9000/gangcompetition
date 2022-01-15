package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    MemberRepository repository;

    @GetMapping("/")
    public String hello(Map<String, Object> model) {
        model.put("members", new ArrayList<>(repository.findAll()));
        return "welcome";
    }

    @GetMapping("/main")
    public String addNewUser(Map<String, Object> model) {
        return "main";
    }

    @PostMapping("/main")
    public String addNewUser(
            @RequestParam String name,
            @RequestParam String email,
            Map<String, Object> model) {
        Member member = new Member(name, email);
        repository.save(member);
        List<Member> memberList = new ArrayList<>(repository.findAll());
        model.put("members", memberList);
        return "main";
    }
}

package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/save")
    public String addUser(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "") String email,
            Map<String, Object> model) {
        Member member = new Member(name, email);
        if (!name.isBlank()) {
            memberRepository.save(member);
        } else throw new IllegalArgumentException("Please fill in the \"name\" field");
        List<Member> memberList = new ArrayList<>(memberRepository.findAll());
        model.put("members", memberList);
        return "userList";
    }
}


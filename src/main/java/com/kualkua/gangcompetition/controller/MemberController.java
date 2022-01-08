package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public @ResponseBody String addUser(
            @RequestParam String name,
            @RequestParam(required = false) String email) {
        Member member = new Member();
        member.setName(name);
        if (email == null) {
            member.setEmail("");
        } else {
            member.setEmail(email);
        }
        memberRepository.save(member);
        return "saved";
    }
}


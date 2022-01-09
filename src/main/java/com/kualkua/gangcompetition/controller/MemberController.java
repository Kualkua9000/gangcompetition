package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/save")
    public String addUser(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "") String email,
            Model model) {
        Member member = new Member(name, email);
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);
        model.addAttribute("members", memberList);
        if (!name.isBlank()) {
            memberRepository.save(member);
        } else throw new RuntimeException();
        return "userSaved";
    }
}


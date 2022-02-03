package com.kualkua.gangcompetition.controller;

import com.kualkua.gangcompetition.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userList", memberRepository.findAll());
        return "userList";
    }
}

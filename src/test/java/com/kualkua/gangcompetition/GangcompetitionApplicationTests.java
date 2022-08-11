package com.kualkua.gangcompetition;

import com.kualkua.gangcompetition.controller.MemberController;
import com.kualkua.gangcompetition.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@SpringBootTest
class GangcompetitionApplicationTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberController memberController;

    @Test
    void contextLoads() {
    }

    @Test
    void controllerTest() {
        memberController.userStravaAuthentication(new RedirectAttributesModelMap());
    }
}

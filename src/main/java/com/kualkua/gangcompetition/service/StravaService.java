package com.kualkua.gangcompetition.service;

import com.kualkua.gangcompetition.client.OAuthToken;
import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.client.impl.StravaClientImpl;
import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.repository.MemberRepository;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StravaService {

    final StravaClient client;

    final MemberRepository memberRepository;

    public StravaService(StravaClientImpl client, MemberRepository memberRepository) {
        this.client = client;
        this.memberRepository = memberRepository;
    }

    public JSONObject getUserLastActivity() {
        client.getAuthString();
        return client.getLastActivity();
    }

    public void initializeToken(String code) {
        OAuthToken jwt = client.getBearer(code);
        client.saveMemberRefresh(jwt.value());
    }
}

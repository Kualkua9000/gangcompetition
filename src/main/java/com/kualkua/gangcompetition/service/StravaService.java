package com.kualkua.gangcompetition.service;

import com.kualkua.gangcompetition.client.OAuthToken;
import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.client.impl.StravaClientImpl;
import com.kualkua.gangcompetition.domain.Activity;
import com.kualkua.gangcompetition.domain.strava.StravaActivityModel;
import com.kualkua.gangcompetition.mapper.StravaActivityMapper;
import com.kualkua.gangcompetition.repository.ActivityRepository;
import com.kualkua.gangcompetition.repository.MemberRepository;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class StravaService {

    final StravaClient stravaClient;
    final MemberRepository memberRepository;
    final ActivityRepository activityRepository;

    public StravaService(StravaClientImpl stravaClient,
                         MemberRepository memberRepository,
                         ActivityRepository activityRepository) {
        this.stravaClient = stravaClient;
        this.memberRepository = memberRepository;
        this.activityRepository = activityRepository;
    }

    public StravaActivityModel[] saveActivities() {
        StravaActivityModel[] activityList = stravaClient.getActivities();
        Activity[] activities = new StravaActivityMapper()
                .convertModelArrayToStravaActivityArray(activityList, getUserId());
        activityRepository.saveAll(Arrays.asList(activities));
        return activityList;
    }

    public JSONObject getUserLastActivity() {
        return stravaClient.getLastActivity();
    }

    public void initializeToken(String code) {
        OAuthToken jwt = stravaClient.getBearer(code);
        stravaClient.saveMemberRefresh(jwt.getRefreshToken());
    }

    public long getUserId() {
        Authentication authentication = getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        } return memberRepository.findByUsername(currentUserName).getId();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

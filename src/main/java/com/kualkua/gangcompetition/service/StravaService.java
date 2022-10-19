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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<Activity> saveActivities() {
        long memberId = getUserId();
        StravaActivityModel[] activityList = stravaClient.getActivities();
        Activity[] activities = new StravaActivityMapper()
                .convertModelArrayToStravaActivityArray(activityList, memberId);
        List<Long> stravaActivityIds = activityRepository.getAllStravaActivitiesId(memberId);
        List<Activity> activitiesToSave = getNewActivities(activities, stravaActivityIds);
        activityRepository.saveAll(activitiesToSave);
        return activitiesToSave;
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

    public List<Activity> getNewActivities(Activity[] activities, List<Long> stravaActivityIds) {
        List<Activity> activitiesToSave = new ArrayList<>(Arrays.asList(activities));
        for (int i=0; i < activities.length; i++) {
            for (Long stravaActivityId : stravaActivityIds) {
                if (activities[i].getStravaActivityId().equals(stravaActivityId)) {
                    activitiesToSave.set(i, null);
                }
            }
        }
        return activitiesToSave.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

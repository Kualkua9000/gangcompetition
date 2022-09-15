package com.kualkua.gangcompetition.client.impl;

import com.kualkua.gangcompetition.client.OAuthToken;
import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.repository.ActivityRepository;
import com.kualkua.gangcompetition.repository.MemberRepository;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class StravaClientImpl implements StravaClient {

    @Value("${clientId}")
    String clientId;

    @Value("${clientSecret}")
    String clientSecret;

    @Value("${stravaTokenUrl}")
    String stravaTokenUrl;

    @Value("${stravaTestUrl}")
    String testUrl;

    @Value("${stravaRetardsClubId}")
    String stravaRetardsClubId;

    private final AtomicReference<OAuthToken> atomicRefToken = new AtomicReference<>(OAuthToken.expiredToken());

    public final ActivityRepository activityRepository;

    public final MemberRepository memberRepository;

    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String STRAVA_ID = "id";

    private static final Logger log = LoggerFactory.getLogger(StravaClientImpl.class);

    public StravaClientImpl(ActivityRepository activityRepository,
                            MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.memberRepository = memberRepository;
    }

    @SneakyThrows
    @Override
    public String getAuthString() {
        return Objects.requireNonNull(Objects.requireNonNull(new RestTemplateBuilder()
                .build()
                .postForLocation("http://www.strava.com/oauth/authorize?client_id=" + clientId +
                                "&response_type=code" +
                                "&redirect_uri=http://localhost:8080/exchange_token&approval_prompt=force" +
                                "&scope=read,activity:read",
                        ResponseEntity.class)).toString());
    }

    @SneakyThrows
    @Override
    public OAuthToken getBearer(String authCode) {
        URI uri = getUriForJwt("code", authCode);
        JSONObject json = new RestTemplateBuilder()
                .build()
                .postForObject(uri,
                        null,
                        JSONObject.class);
        LinkedHashMap stravaAthlete = (LinkedHashMap) Objects.requireNonNull(json).get("athlete");
        Integer stravaId = (Integer) stravaAthlete.get("id");
        saveStravaId(stravaId);
        atomicRefToken.set(initToken(json));
        return initToken(json);
    }

    private void saveStravaId(Number stravaId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        Member member = memberRepository.findByUsername(currentUserName);
        member.setStravaId(stravaId.longValue());
        memberRepository.save(member);
    }

    @SneakyThrows
    @Override
    public OAuthToken updateBearer(String refreshToken) {
        URI uri = getUriForJwt(REFRESH_TOKEN, refreshToken);
        JSONObject json = new RestTemplateBuilder()
                .build()
                .postForObject(uri,
                        null,
                        JSONObject.class);
        saveMemberRefresh(
                Objects.requireNonNull(json)
                        .getAsString(REFRESH_TOKEN));
        return initToken(json);
    }

    @Override
    public JSONObject getUserInfo(String jwt) {
        return new RestTemplateBuilder()
                .build()
                .getForObject(testUrl, JSONObject.class);
    }

    @Override
    public JSONObject getLastActivity() {
        JSONObject json = new RestTemplateBuilder()
                .defaultHeader("Authorization", atomicRefToken.get().value())
                .build()
                .getForObject("https://www.strava.com/api/v3/athletes/21288485/stats",
                        JSONObject.class);
        String jsonResponse = json != null ? json.toJSONString() : "undefined";
        log.info(jsonResponse);
        return new JSONObject();
    }

    @Override
    public void saveMemberRefresh(String token) {
        Authentication authentication = getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        Member member = memberRepository.findByUsername(currentUserName);
        member.setRefreshToken(token);
        memberRepository.save(member);
    }

    @Override
    public JSONObject getActivities() {
        //8b0703ee1f9e2f8b39ffffca5bf85c678697661b
        JSONObject json = new RestTemplateBuilder()
                .defaultHeader("Authorization", atomicRefToken.get().value())
                .build()
                .getForObject("https://www.strava.com/api/v3/athlete/activities",
                        JSONObject.class);
        return json;
    }

    public OAuthToken getToken() {
        if (atomicRefToken.get().isExpired()) {
            atomicRefToken.set(this.fetchToken());
        }
        return atomicRefToken.get();
    }

    private OAuthToken fetchToken() {
        OAuthToken token = updateBearer(
                memberRepository
                        .findByUsername(getUserName())
                        .getRefreshToken());
        saveMemberRefresh(token.value());
        return token;
    }

    private URI getUriForJwt(String authParam, String authValue) {
        return UriComponentsBuilder.fromUriString(stravaTokenUrl)
                .queryParam(authParam, authValue)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam(REFRESH_TOKEN, "authorization_code")
                .build()
                .toUri();
    }


    private OAuthToken initToken(JSONObject json) {
        // TODO: 15.09.2022 ma be we can to use initToken only for getting token after registration in strava
        return new OAuthToken(
                OAuthToken.TOKEN_TYPE_BEARER,
                Objects.requireNonNull(json).getAsString(ACCESS_TOKEN),
                Long.parseLong(Objects.requireNonNull(json).getAsString(EXPIRES_IN)),
                Objects.requireNonNull(json).getAsString(REFRESH_TOKEN));
    }

    private String getUserName() {
        Authentication authentication = getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        return currentUserName;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

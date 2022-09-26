package com.kualkua.gangcompetition.client.impl;

import com.kualkua.gangcompetition.client.OAuthToken;
import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.repository.ActivityRepository;
import com.kualkua.gangcompetition.repository.MemberRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import net.minidev.json.JSONArray;
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

    @Value("${stravaBaseUrl}")
    String stravaBaseUrl;

    private final AtomicReference<OAuthToken> atomicToken = new AtomicReference<>(OAuthToken.expiredToken());

    public final ActivityRepository activityRepository;

    public final MemberRepository memberRepository;

    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    private static final String REFRESH_TOKEN = "refresh_token";

    private static final Logger log = LoggerFactory.getLogger(StravaClientImpl.class);

    public StravaClientImpl(ActivityRepository activityRepository,
                            MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.memberRepository = memberRepository;
    }

    public void startInitiatingToken() {
        if(atomicToken.get().getRefreshToken().equals("expired")) {
            atomicToken.set(new AtomicReference<>(updateBearer(memberRepository
                    .findByUsername(getUserName()).getRefreshToken())).get());
        }
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
        URI uri = getUriForJwt(authCode);
        JSONObject json = new RestTemplateBuilder()
                .build()
                .postForObject(uri,
                        null,
                        JSONObject.class);
        @NonNull
        LinkedHashMap<String, Integer> stravaAthlete = (LinkedHashMap<String, Integer>) json.get("athlete");
        Integer stravaId = stravaAthlete.get("id");
        saveStravaId(stravaId);
        atomicToken.set(initToken(json));
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
        URI uri = getUriForUpdateJwt(refreshToken);
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
                .getForObject(stravaBaseUrl, JSONObject.class);
    }

    @Override
    public JSONObject getLastActivity() {
        JSONObject json = new RestTemplateBuilder()
                .defaultHeader("Authorization", atomicToken.get().value())
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
    public JSONArray getActivities() {
        startInitiatingToken();
        return new RestTemplateBuilder()
                .defaultHeader("Authorization", atomicToken.get().value())
                .build()
                .getForObject("https://www.strava.com/api/v3/athlete/activities",
                        JSONArray.class);
    }

    public OAuthToken getToken() {
        if (atomicToken.get().isExpired()) {
            atomicToken.set(this.fetchToken());
        }
        return atomicToken.get();
    }

    private OAuthToken fetchToken() {
        OAuthToken token = updateBearer(
                memberRepository
                        .findByUsername(getUserName())
                        .getRefreshToken());
        saveMemberRefresh(token.getRefreshToken());
        return token;
    }

    private URI getUriForJwt(String authValue) {
        return UriComponentsBuilder.fromUriString(stravaTokenUrl)
                .queryParam("code", authValue)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                //.queryParam(REFRESH_TOKEN, "authorization_code")
                .build()
                .toUri();
    }

    private URI getUriForUpdateJwt(String authValue) {
        return UriComponentsBuilder.fromUriString(stravaTokenUrl)
                .queryParam(REFRESH_TOKEN, authValue)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", "refresh_token")
                .build()
                .toUri();
    }


    private OAuthToken initToken(JSONObject json) {
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

package com.kualkua.gangcompetition.client.impl;

import com.kualkua.gangcompetition.client.OAuthToken;
import com.kualkua.gangcompetition.client.StravaClient;
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
                                "&response_type=code&redirect_uri=http://localhost:8080/exchange_token&approval_prompt=force&scope=read",
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
        return initToken(json);
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
        return initToken(json);
    }

    @Override
    public JSONObject getUserInfo(String jwt) {
        JSONObject json = new RestTemplateBuilder()
                .build()
                .getForObject(testUrl, JSONObject.class);
        return json;
    }

    @Override
    public JSONObject getLastActivity() {
        JSONObject json = new RestTemplateBuilder()
                .defaultHeader("Authorization", "")
                .build()
                .getForObject("https://www.strava.com/api/v3/athletes/21288485/stats", JSONObject.class);
        String jsonResponse = json != null ? json.toJSONString() : "undefined";
        log.info(jsonResponse);
        return new JSONObject();
    }

    public OAuthToken getToken() {
        if (atomicRefToken.get().isExpired()) {
            atomicRefToken.set(this.fetchToken());
        }
        return atomicRefToken.get();
    }

    private OAuthToken fetchToken() {
        return new OAuthToken(
                OAuthToken.TOKEN_TYPE_BEARER,
                updateBearer(memberRepository
                        .findByUsername(getUserName())
                        .getRefreshToken())
                        .value(),
                0L);
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
        return new OAuthToken(
                OAuthToken.TOKEN_TYPE_BEARER,
                Objects.requireNonNull(json).getAsString(ACCESS_TOKEN),
                Long.parseLong(Objects.requireNonNull(json).getAsString(EXPIRES_IN)));
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        } return currentUserName;
    }
}

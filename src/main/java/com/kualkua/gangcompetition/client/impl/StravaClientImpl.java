package com.kualkua.gangcompetition.client.impl;

import com.kualkua.gangcompetition.client.OAuthToken;
import com.kualkua.gangcompetition.client.StravaClient;
import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.domain.strava.StravaActivityModel;
import com.kualkua.gangcompetition.dto.StravaTokenResponseDto;
import com.kualkua.gangcompetition.repository.ActivityRepository;
import com.kualkua.gangcompetition.repository.MemberRepository;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StravaClientImpl implements StravaClient {

    @Value("${clientId}")
    String clientId;

    @Value("${clientSecret}")
    String clientSecret;

    @Value("${stravaTokenUrl}")
    String stravaTokenUrl;

    @Value("${stravaBaseUrl}")
    String stravaBaseUrl;

    @Value("${stravaRedirectUri}")
    String stravaRedirectUri;

    private final AtomicReference<OAuthToken> atomicToken = new AtomicReference<>(OAuthToken.expiredToken());

    public final ActivityRepository activityRepository;

    public final MemberRepository memberRepository;

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
                                "&response_type=code" +
                                "&redirect_uri=" + stravaRedirectUri +
                                "&scope=read,activity:read",
                        ResponseEntity.class)).toString());
    }

    @SneakyThrows
    @Override
    public OAuthToken getBearer(String authCode) {
        URI uri = getUriForJwt(authCode);
        StravaTokenResponseDto responseDto = new RestTemplateBuilder()
                .build()
                .postForObject(uri,
                        null,
                        StravaTokenResponseDto.class);
        int stravaId = Objects.requireNonNull(responseDto).getAthlete().getId();
        saveStravaId(stravaId);
        atomicToken.set(initToken(responseDto));
        return initToken(responseDto);
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
        StravaTokenResponseDto responseDto = new RestTemplateBuilder()
                .build()
                .postForObject(uri,
                        null,
                        StravaTokenResponseDto.class);
        saveMemberRefresh(Objects.requireNonNull(responseDto).getRefreshToken());
        return initToken(responseDto);
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
                .defaultHeader("Authorization", getAtomicToken().value())
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
    public StravaActivityModel[] getActivities() {
        getAtomicToken();
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder
                .defaultHeader("Authorization", atomicToken.get().value())
                .build()
                .getForObject("https://www.strava.com/api/v3/activities/?per_page=100",
                        StravaActivityModel[].class);
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
                .build()
                .toUri();
    }

    private URI getUriForUpdateJwt(String authValue) {
        return UriComponentsBuilder.fromUriString(stravaTokenUrl)
                .queryParam(REFRESH_TOKEN, authValue)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("grant_type", REFRESH_TOKEN)
                .build()
                .toUri();
    }


    private OAuthToken initToken(StravaTokenResponseDto responseDto) {
        return new OAuthToken(
                responseDto.getTokenType(),
                responseDto.getAccessToken(),
                responseDto.getExpiresIn(),
                responseDto.getRefreshToken());
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

    public OAuthToken getAtomicToken() {
        if (atomicToken.get().isExpired()) {
            atomicToken.set(this.fetchToken());
        }
        return atomicToken.get();
    }
}

package com.kualkua.gangcompetition.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OAuthToken implements Serializable {

    public static final String TOKEN_TYPE_BEARER = "Bearer";

    public static final Long SPARE_SECONDS = 100L;

    private final String tokenType;
    private final String accessToken;
    private final Long expiresIn;
    private final LocalDateTime expiresAt;

    public OAuthToken(@JsonProperty("token_type") @NonNull String tokenType,
                      @JsonProperty("access_token") @NonNull String accessToken,
                      @JsonProperty("expires_in") @NonNull Long expiresIn) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.expiresAt = LocalDateTime.now().plus(expiresIn - SPARE_SECONDS, ChronoUnit.SECONDS);
    }

    public static OAuthToken expiredToken() {
        return new OAuthToken(TOKEN_TYPE_BEARER, "expired", 0L);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public String value() {
        return this.accessToken;
    }

}
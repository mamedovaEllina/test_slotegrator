package rest_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class AccessToken {

    private static final int DRIFT = 15;
    private final String tokenType;
    private final int expiresIn;
    private final String accessToken;
    private final String refreshToken;
    @JsonIgnore
    private Instant expireAccessTokenTime;

    @JsonCreator
    public AccessToken(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("expires_in") int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        setExpireAccessTokenTime(Instant.now().plusSeconds(expiresIn - DRIFT));
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @JsonIgnore
    public Instant getExpireAccessTokenTime() {
        return expireAccessTokenTime;
    }

    @JsonIgnore
    private void setExpireAccessTokenTime(Instant expireAccessTokenTime) {
        this.expireAccessTokenTime = expireAccessTokenTime;
    }
}

package rest_api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import io.restassured.http.ContentType;

import java.time.Instant;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest_api.model.AccessToken;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthHelper {
    private static final Logger LOGGER = LogManager.getLogger(AuthHelper.class);
    private static final String AUTH_BASE_URL = "http://test-api.d6.dev.devcaz.com/v2/oauth2";
    private static final String TOKEN_RESOURCE = "/token";
    private static final String BASIC_TOKEN = "front_2d6b0a8391742f5d789d7d915755e09e";
    private static AccessToken tokensSet = null;

    public static AccessToken getGuestToken() {
        LOGGER.info("Выполняю запрос токена авторизации гостевой");
        Map<String, String> params = Map.of(
                "scope", "guest:default",
                "grant_type", "client_credentials");
        var response = given()
                .baseUri(AUTH_BASE_URL)
                .basePath(TOKEN_RESOURCE)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().preemptive().basic(BASIC_TOKEN, "-")
                .body(params)
                .post();
        assertThat(response.getStatusCode()).isEqualTo(SC_OK);
        LOGGER.debug("Получен ответ - \n" + response.getBody());
        return response.as(AccessToken.class);
    }

    public static AccessToken getUserToken(final String login, final String password) {
        Map<String, String> params = Map.of(
                "username", login,
                "password", password,
                "grant_type", "password");
        var response = given()
                .baseUri(AUTH_BASE_URL)
                .basePath(TOKEN_RESOURCE)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().preemptive().basic(BASIC_TOKEN, "-")
                .body(params)
                .post();
        assertThat(response.getStatusCode()).isEqualTo(SC_OK);
        tokensSet = response.as(AccessToken.class);
        return tokensSet;
    }

    // todo вопрос о ролевой модели - как определяют юзера
    public static AccessToken getToken(final String login, final String password) {
        Map<String, String> params = Map.of();
        if (tokensSet == null // || !decode(tokensSet.getAccessToken()).equals(login)
        ) {
            LOGGER.info("Выполняю запрос токена авторизации");
            params = Map.of(
                    "username", login,
                    "password", password,
                    "grant_type", "password");

        } else if (Instant.now().isAfter(tokensSet.getExpireAccessTokenTime())) {
            LOGGER.info("Выполняю запрос обновления токена авторизации");
            params = Map.of(
                    "grant_type", "refresh_token",
                    "refresh_token", tokensSet.getRefreshToken());
        } else {
            return tokensSet;
        }
        var response = given()
                .baseUri(AUTH_BASE_URL)
                .basePath(TOKEN_RESOURCE)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().preemptive().basic(BASIC_TOKEN, "-")
                .body(params)
                .post();
        assertThat(response.getStatusCode()).isEqualTo(SC_OK);
        tokensSet = response.as(AccessToken.class);
        return tokensSet;
    }

    private static String decode(String accessToken) {
        try {
            return JWT.decode(accessToken)
                    .getClaims()
                    .get("username")
                    .asString()
                    .toLowerCase()
                    .replaceAll(" ", "_");
        } catch (JWTDecodeException exception) {
            throw new IllegalArgumentException();
        }
    }

}

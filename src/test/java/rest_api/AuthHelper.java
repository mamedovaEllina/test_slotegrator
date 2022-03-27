package rest_api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import io.restassured.http.ContentType;

import java.time.Instant;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class AuthHelper {
    private static final Logger LOGGER = LogManager.getLogger(AuthHelper.class);
    private static final String AUTH_BASE_URL = "http://test-api.d6.dev.devcaz.com/v2/oauth2";
    private static final String TOKEN_RESOURCE = "/token";
    private static AccessToken tokensSet = null;

    public static AccessToken getGuestToken() {
        LOGGER.info("Выполняю запрос токена авторизации гостевой");
        Map<String, String> formParams = Map.of(
                "scope", "guest:default",
                "grant_type", "client_credentials");
        tokensSet = given()
                .relaxedHTTPSValidation()
                .baseUri(AUTH_BASE_URL)
                .basePath(TOKEN_RESOURCE)
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .post()
                .then().log().ifError().statusCode(SC_OK)
                .extract().body().as(AccessToken.class);
        LOGGER.debug("Получен ответ\n" + tokensSet.toString());
        return tokensSet;

    }

    public static AccessToken getToken(final String login, final String password) {
        if (tokensSet == null
                || Instant.now().isAfter(tokensSet.getExpireRefreshTokenTime())
                || !decode(tokensSet.getAccessToken()).equals(login)) {
            LOGGER.info("Выполняю запрос токена авторизации");
            Map<String, String> formParams = Map.of(
                    "username", login,
                    "password", password,
                    "grant_type", "password");
            tokensSet = given()
                    .relaxedHTTPSValidation()
                    .baseUri(AUTH_BASE_URL)
                    .basePath(TOKEN_RESOURCE)
                    .contentType(ContentType.URLENC)
                    .formParams(formParams)
                    .post()
                    .then().log().ifError().statusCode(SC_OK)
                    .extract().body().as(AccessToken.class);
            LOGGER.debug("Получен ответ\n" + tokensSet.toString());
            return tokensSet;

        } else if (Instant.now().isAfter(tokensSet.getExpireAccessTokenTime())) {
            LOGGER.info("Выполняю запрос обновления токена авторизации");
            Map<String, String> formParams = Map.of(
                    "grant_type", "refresh_token",
                    "refresh_token", tokensSet.getRefreshToken());
            tokensSet = given()
                    .relaxedHTTPSValidation()
                    .baseUri(AUTH_BASE_URL)
                    .basePath(TOKEN_RESOURCE)
                    .contentType(ContentType.URLENC)
                    .formParams(formParams)
                    .post()
                    .then().log().ifError().statusCode(SC_OK)
                    .extract().body().as(AccessToken.class);
        }
        return tokensSet;
    }
    /* - guest
    ------------
    {     "grant_type":"client_credentials",
    "scope":"guest:default" }
    //------------------
    * "token_type":"Bearer",
    * "expires_in":3600,
    * "access_token":"2YotnFZFEjr1zCsicMWpAA",
    * "refresh_token":"def50200fcc006121b6a068eced57,
    */

    /* - user
    //-----------------
    //{     "grant_type":"password",
    // "username":"johndoe",
    // "password":"am9obmRvZTEyMw==" }
    //------------------
    "token_type":"Bearer",
    "expires_in":3600,
    "access_token":"2YotnFZFEjr1zCsicMWpAA",
    "refresh_token":"def50200fcc006121b6a068eced57,
     *
     */

    /**
     * Decode JWT.
     *
     * @param accessToken accessToken
     * @return login
     */
    private static String decode(String accessToken) {
        try {
            return JWT.decode(accessToken)
                    .getClaims()
                    .get("name")
                    .asString()
                    .toLowerCase()
                    .replaceAll(" ", "_");
        } catch (JWTDecodeException exception) {
            throw new IllegalArgumentException();
        }
    }

}

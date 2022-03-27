package rest_api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.TestNGException;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static rest_api.AuthHelper.getGuestToken;
import static rest_api.AuthHelper.getToken;

public class BaseSteps {
    private static final Logger LOGGER = LogManager.getLogger(BaseSteps.class);
    private static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
            .setBaseUri("http://test-api.d6.dev.devcaz.com/v2/oauth2")
            .setBasePath("/v2/players")
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .build();;

    public Response addPlayer(NewPlayer newPlayer) {
        LOGGER.info("Выполняю запрос на добавление игрока");
//        RequestSpecification spec = new RequestSpecBuilder()
//        .addRequestSpecification(BASE_SPEC)
//        .setBasePath("/v2/players")
//        .build();
        final var response = given()
                .spec(BASE_SPEC)
                .auth().oauth2(getGuestToken().getAccessToken())
                .contentType(ContentType.JSON)
                .body(newPlayer)
                .post();
        describeResponse(LOGGER, response);
        return response;
    }

    public Response getPlayer(final int id, final String email, final String password) {
        LOGGER.info("Выполняю запрос на получение игрока:\n" + id);
        final var response = given()
                .spec(BASE_SPEC)
                .auth().oauth2(getToken(email, password).getAccessToken())
                .get(String.valueOf(id));
        describeResponse(LOGGER, response);
        return response;
    }

    public static void describeResponse(final Logger logger, final Response response) {
        logger.info(String.format("Получен ответ: %d\n%s",
                response.getStatusCode(),
                response.prettyPrint()));
    }
    public static synchronized String describeBusinessObject(final Object o) {
        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new TestNGException(e.toString());
        }
    }
}
package rest_api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest_api.model.NewPlayer;

import static io.restassured.RestAssured.given;

public class BaseSteps {
    private static final Logger LOGGER = LogManager.getLogger(BaseSteps.class);
    private static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
            .setBaseUri("http://test-api.d6.dev.devcaz.com")
            .setBasePath("/v2/players")
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .build();

    public Response addPlayer(NewPlayer newPlayer, String token) {
        LOGGER.info("Выполняю запрос на добавление игрока");
        final var response = given()
                .spec(BASE_SPEC)
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(newPlayer)
                .post();
        describeResponse(LOGGER, response);
        return response;
    }

    public Response getPlayer(final int id, String token) {
        LOGGER.info("Выполняю запрос на получение игрока:\n" + id);
        final var response = given()
                .spec(BASE_SPEC)
                .auth().oauth2(token)
                .get(String.valueOf(id));
        describeResponse(LOGGER, response);
        return response;
    }

    public static void describeResponse(final Logger logger, final Response response) {
        logger.info(String.format("Получен ответ: %d\n%s",
                response.getStatusCode(),
                response.prettyPrint()));
    }
}
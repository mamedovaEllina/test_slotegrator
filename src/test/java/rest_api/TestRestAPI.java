package rest_api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rest_api.model.NewPlayer;
import rest_api.model.Player;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static rest_api.AuthHelper.*;

public class TestRestAPI {
    private static final Logger LOGGER = LogManager.getLogger(TestRestAPI.class);

    protected static final BaseSteps BASE_STEPS = new BaseSteps();
    protected static final String SHORT_RAND = randomAlphabetic(5);

    /* •username: string, required, unique user's login
     * •password_change: string, required, base64 encoded password
     * •password_repeat: string, required, base64 encoded password repeat
     * •email: string, required, user's email
     * •name: string, optional, user's first name
     * •surname: string, optional, user's last name
     * •currency_code: string, optional, default currency code according to ISO4217*/

    protected static final NewPlayer BASE_PLAYER = new NewPlayer.Builder()
            .setEmail(SHORT_RAND + "_janedoe@example.com")
            .setPasswordChange("amFuZWRvZTEyMw==")
            .setPasswordRepeat("amFuZWRvZTEyMw==")
            .setUsername(SHORT_RAND + "Jane")
            .setName(SHORT_RAND + "Jane")
            .setSurname("Doe")
            .setCurrencyCode("RUB")
            .build();

    @DataProvider
    public static Object[][] positiveDataProvider() {
        return new Object[][]{
                {BASE_PLAYER},
                {new NewPlayer.Builder().using(BASE_PLAYER)
                        .setUsername(randomAlphabetic(5) + "Jane")
                        .setEmail(randomAlphabetic(5) + "_janedoe@example.com")
                        .setName(null)
                        .build()},
                {new NewPlayer.Builder().using(BASE_PLAYER)
                        .setUsername(randomAlphabetic(5) + "Jane")
                        .setEmail(randomAlphabetic(5) + "_janedoe@example.com")
                        .setSurname(null).build()},
                {new NewPlayer.Builder().using(BASE_PLAYER)
                        .setUsername(randomAlphabetic(5) + "Jane")
                        .setEmail(randomAlphabetic(5) + "_janedoe@example.com")
                        .setCurrencyCode(null).build()}
        };
    }

    @Test(dataProvider = "positiveDataProvider")
    private void positiveTest(NewPlayer body) {
        var getResponse = BASE_STEPS.addPlayer(body, getGuestToken().getAccessToken());
        assertThat(getResponse.getStatusCode()).isEqualTo(SC_CREATED);
        Player player = getResponse.as(Player.class);
        int id = Integer.parseInt(player.getId());
        player.partlyEquals(body);

        var userToken = getUserToken(body.getUsername(), body.getPasswordChange()).getAccessToken();
        getResponse = BASE_STEPS.getPlayer(id, userToken);
        assertThat(getResponse.getStatusCode()).isEqualTo(SC_OK);
        Player getPlayer = getResponse.as(Player.class);
        getPlayer.partlyEquals(body);

        getResponse = BASE_STEPS.getPlayer(id + 777, userToken);
        assertThat(getResponse.getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @DataProvider
    public static Object[][] negativeDataProvider() {
        return new Object[][]{
                {new NewPlayer.Builder().using(BASE_PLAYER).setEmail(null).build()},
                {new NewPlayer.Builder().using(BASE_PLAYER).setUsername(null).build()},
                {new NewPlayer.Builder().using(BASE_PLAYER).setPasswordChange(null).build()},
                {new NewPlayer.Builder().using(BASE_PLAYER).setPasswordRepeat(null).build()}
        };
    }

    @Test(dataProvider = "negativeDataProvider")
    private void negativeTest(NewPlayer body) {
        var getResponse = BASE_STEPS.addPlayer(body, getGuestToken().getAccessToken());
        assertThat(getResponse.getStatusCode()).isEqualTo(SC_UNPROCESSABLE_ENTITY);
    }
}

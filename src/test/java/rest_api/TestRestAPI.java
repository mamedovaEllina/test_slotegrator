package rest_api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TestRestAPI {
    private static final Logger LOGGER = LogManager.getLogger(TestRestAPI.class);

    protected static final BaseSteps BASE_STEPS = new BaseSteps();
   /* •username: string, required, unique user's login
    * •password_change: string, required, base64 encoded password
    * •password_repeat: string, required, base64 encoded password repeat
    * •email: string, required, user's email
    * •name: string, optional, user's first name
    * •surname: string, optional, user's last name
    * •currency_code: string, optional, default currency code according to ISO4217*/

    protected static final NewPlayer BASE_PLAYER = new NewPlayer.Builder()
            .setEmail("janedoe@example.com")
            .setPasswordChange("amFuZWRvZTEyMw==")
            .setPasswordRepeat("amFuZWRvZTEyMw==")
            .setUsername("janedoe")
            .setName("Jane")
            .setSurname("Doe")
            .setCurrencyCode("EUR")
            .build();

    @Test()
    private void baseTest() {
        var getResponse = BASE_STEPS.addPlayer(BASE_PLAYER);
        assertThat(getResponse.getStatusCode()).isEqualTo(SC_CREATED);
        Player player = getResponse.as(Player.class);
        int id = Integer.parseInt(player.getId());
        player.partlyEquals(BASE_PLAYER);

        getResponse = BASE_STEPS.getPlayer(id, BASE_PLAYER.getEmail(), BASE_PLAYER.getPasswordChange());
        assertThat(getResponse.getStatusCode()).isEqualTo(SC_OK);
        Player getPlayer = getResponse.as(Player.class);
        getPlayer.partlyEquals(player);

        getResponse = BASE_STEPS.getPlayer(id, BASE_PLAYER.getEmail(), BASE_PLAYER.getPasswordChange());
        assertThat(getResponse.getStatusCode()).isEqualTo(SC_OK);
        player = getResponse.as(Player.class);
        getPlayer.partlyEquals(player);

        getResponse = BASE_STEPS.getPlayer(id + 777, BASE_PLAYER.getEmail(), BASE_PLAYER.getPasswordChange());
        assertThat(getResponse.getStatusCode()).isEqualTo(SC_NOT_FOUND);
        player = getResponse.as(Player.class);
        getPlayer.partlyEquals(player);
    }
}

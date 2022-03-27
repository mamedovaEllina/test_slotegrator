package selenium_ui;

import com.codeborne.selenide.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import rest_api.TestRestAPI;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static selenium_ui.DriverHelper.getDriver;
import static selenium_ui.DriverHelper.killDriver;

public class TestUI {
    private static final Logger LOGGER = LogManager.getLogger(TestUI.class);

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        getDriver();
        Configuration.timeout = 5000;
    }

    @AfterSuite(description = "Закрываю сессию web-driver'а", alwaysRun = true)
    public void afterMethod() {
        killDriver();
    }


    @Test
    public void userCanLoginByUsername() {
        open("https://habr.com/ru/all/");
//        $(By.name("user.name")).setValue("johny");
//        $("#submit").click();
        $(".loading_progress").should(disappear); // Waits until element disappears
        $("#username").shouldHave(text("Hello, Johny!")); // Waits until element gets text
    }
}

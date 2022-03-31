package selenium_ui.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class StepDefinitions {
    @Given("^I logged in$")
    public void i_am_on_the_page_on_URL(String arg1, String arg2) throws Throwable {
        open("http://test-app.d6.dev.devcaz.com/admin/login");
        $(By.id("UserLogin_username")).setValue("-");
        $(By.id("UserLogin_password")).setValue("-");
        $(By.cssSelector("[value=\"Sign in\"]")).click();
    }

    @When("^I fill in \"([^\"]*)\" with \"([^\"]*)\"$")
    public void i_fill_in_with(String arg1, String arg2) throws Throwable {
        throw new PendingException();
    }

    @When("^I click on the \"([^\"]*)\" button$")
    public void i_click_on_the_button(String arg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^I should see \"([^\"]*)\" message$")
    public void i_should_see_message(String arg1) throws Throwable {
        throw new PendingException();
    }

}
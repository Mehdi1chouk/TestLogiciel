package stepdefenition;

import base.TestBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;

public class LoginStep extends TestBase {

    private LoginPage loginPage;

    public LoginStep() {

        loginPage = new LoginPage(getDriver());
    }

    @Given("the user is on the login page")
    public void userIsOnLoginPage() {
        loginPage.openLoginPage();
    }

    @When("the user enters a username")
    public void the_user_enters_a_username() {
        loginPage.enterUsername("tomsmith");  // Hardcoded valid username
    }

    @When("the user enters a password")
    public void the_user_enters_a_password() {
        loginPage.enterPassword("SuperSecretPassword!");  // Hardcoded valid password
    }

    @And("clicks on the login button")
    public void clicks_on_the_login_button() {
        loginPage.clickLoginButton();
    }

    @Then("the user should see a successful login message")
    public void the_user_should_see_a_successful_login_message() {
        String msg = loginPage.getSuccessMessage();
        Assertions.assertTrue(
                msg.contains("You logged into a secure area!"),
                "Le message de succès n'est pas correct!"
        );
    }
}
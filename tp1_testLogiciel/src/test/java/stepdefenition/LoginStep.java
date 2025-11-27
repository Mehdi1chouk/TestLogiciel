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

    @When("the user enters a username as {string}")
    public void the_user_enters_a_username(String username) {

        loginPage.enterUsername(username);  // Hardcoded valid username
    }

    @When("the user enters a password as {string}")
    public void the_user_enters_a_password(String password) {
        loginPage.enterPassword(password);  // Hardcoded valid password
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


    @Then("the user should see an error message")
    public void the_user_should_see_an_error_login_message() {
        String msg = loginPage.Errormessage();
        Assertions.assertTrue(
                msg.contains("Your username is invalid!") || msg.contains("Your password is invalid!"),
                "Le message d'erreur n'est pas correct! Message reçu: " + msg
        );
    }



}
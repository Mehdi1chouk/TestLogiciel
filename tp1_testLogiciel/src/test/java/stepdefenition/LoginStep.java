package stepdefenition;

import base.TestBase;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import ExtentReport.ExtentReportManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;

import java.io.File;

public class LoginStep extends TestBase {

    private LoginPage loginPage;

    public LoginStep() {
        loginPage = new LoginPage(getDriver());
    }

    @Given("the user is on the login page")
    public void userIsOnLoginPage() {
        try {
            loginPage.openLoginPage();
            ExtentReportManager.getTest().log(Status.PASS, "The user is on the login page");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to open the login page");
            throw e;
        }
    }

    @When("the user enters a username as {string}")
    public void the_user_enters_a_username(String username) {
        try {
            loginPage.enterUsername(username);
            ExtentReportManager.getTest().log(Status.PASS, "Entered username: " + username);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter username: " + username);
            throw e;
        }
    }

    @When("the user enters a password as {string}")
    public void the_user_enters_a_password(String password) {
        try {
            loginPage.enterPassword(password);
            ExtentReportManager.getTest().log(Status.PASS, "Entered password");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter password");
            throw e;
        }
    }

    @And("clicks on the login button")
    public void clicks_on_the_login_button() {
        try {
            loginPage.clickLoginButton();
            ExtentReportManager.getTest().log(Status.PASS, "Clicked on login button");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to click on login button");
            throw e;
        }
    }

    @Then("the user should see a successful login message")
    public void the_user_should_see_a_successful_login_message() {
        try {
            String msg = loginPage.getSuccessMessage();

            Assertions.assertTrue(
                    msg.contains("You logged into a secure area!"),
                    "Le message de succès n'est pas correct!"
            );

            ExtentReportManager.getTest().log(Status.PASS, "Success message displayed correctly");

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Assertion failed for success message: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying success message");
            throw e;
        }
    }

    @Then("the user should see an error message")
    public void the_user_should_see_an_error_login_message() {
        try {
            String msg = loginPage.Errormessage();

            Assertions.assertTrue(
                    msg.contains("Your username is invalid!") || msg.contains("Your password is invalid!"),
                    "Le message d'erreur n'est pas correct! Message reçu: " + msg
            );

            ExtentReportManager.getTest().log(Status.PASS, "Error message displayed correctly");

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Assertion failed for error message: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying login error message");
            throw e;
        }
    }

    private void attachScreenshotOnFailure(String message) {
        try {
            // Absolute path from captureScreenshot
            String absolutePath = TestBase.captureScreenshot(
                    System.currentTimeMillis() + "_screenshot"
            );

            // Make path relative to the report location
            String relativePath = "../screenshots/" + new File(absolutePath).getName();

            ExtentReportManager.getTest().fail(
                    message,
                    MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build()
            );

        } catch (Exception e) {
            ExtentReportManager.getTest().fail("Failed to attach screenshot: " + e.getMessage());
        }
    }


}

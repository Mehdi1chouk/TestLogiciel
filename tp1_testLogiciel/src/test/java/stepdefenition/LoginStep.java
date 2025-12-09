package stepdefenition;

import base.TestBase;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import ExtentReport.ExtentReportManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
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
            ExtentReportManager.getTest().log(Status.PASS, "Navigated to login page");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to open login page");
            throw e;
        }
    }

    @When("the user enters an email as {string}")
    public void the_user_enters_an_email(String email) {
        try {
            loginPage.enterEmail(email);
            ExtentReportManager.getTest().log(Status.PASS, "Entered email: " + email);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter email: " + email);
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

    @When("the user clicks the login button")
    public void clicks_on_login_button() throws InterruptedException {
        try {
            loginPage.clickLoginButton();
            Thread.sleep(1500);
            ExtentReportManager.getTest().log(Status.PASS, "Clicked login button");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to click login button");
            throw e;
        }
    }

    @Then("the user should be redirected to the home page")
    public void the_user_should_be_redirected_to_home_page() {
        try {
            boolean isRedirected = loginPage.isRedirectedToHomePage();

            Assertions.assertTrue(
                    isRedirected,
                    "Login failed. User was not redirected to home page. Current URL: " + getDriver().getCurrentUrl()
            );

            ExtentReportManager.getTest().log(Status.PASS, "Successfully logged in and redirected");

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Login success validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying login success");
            throw e;
        }
    }

    @Then("the user should see an error message")
    public void the_user_should_see_an_error_message() {
        try {
            String msg = loginPage.getErrorMessage();

            Assertions.assertTrue(
                    !msg.isEmpty() && (msg.toLowerCase().contains("invalid") ||
                            msg.toLowerCase().contains("error") ||
                            msg.toLowerCase().contains("failed")),
                    "Expected error message but got: '" + msg + "'"
            );

            ExtentReportManager.getTest().log(Status.PASS, "Error message displayed: " + msg);

            /* ------------- ADD THIS PART (same as register negative case) ------------- */
            String screenshotPath = TestBase.captureScreenshot("FailedLogin_ExpectedBehavior");
            String relativePath = "../screenshots/" + new File(screenshotPath).getName().replace("target/", "");

            ExtentReportManager.getTest().pass("Login failed as expected",
                    MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
            /* --------------------------------------------------------------------------- */

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Error message validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying error message");
            throw e;
        }
    }


    private void attachScreenshotOnFailure(String message) {
        try {
            String absolutePath = TestBase.captureScreenshot(System.currentTimeMillis() + "_screenshot");
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
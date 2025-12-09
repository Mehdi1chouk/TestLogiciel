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
import pages.RegisterPage;
import java.io.File;

public class RegisterStep extends TestBase {
    private RegisterPage registerPage;

    public RegisterStep() {
        registerPage = new RegisterPage(getDriver());
    }

    @Given("the user is on the registration page")
    public void userIsOnRegisterPage() {
        try {
            registerPage.openRegisterPage();
            ExtentReportManager.getTest().log(Status.PASS, "User navigated to registration page");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to open registration page");
            throw e;
        }
    }



    @When("the user enters first name as {string}")
    public void the_user_enters_first_name(String firstName) {
        try {
            registerPage.enterFirstName(firstName);
            ExtentReportManager.getTest().log(Status.PASS, "Entered first name: " + firstName);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter first name");
            throw e;
        }
    }

    @When("the user enters last name as {string}")
    public void the_user_enters_last_name(String lastName) {
        try {
            registerPage.enterLastName(lastName);
            ExtentReportManager.getTest().log(Status.PASS, "Entered last name: " + lastName);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter last name");
            throw e;
        }
    }

    @When("the user enters email as {string}")
    public void the_user_enters_email(String email) {
        try {
            registerPage.enterEmail(email);
            ExtentReportManager.getTest().log(Status.PASS, "Entered email: " + email);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter email");
            throw e;
        }
    }

    @When("the user enters password as {string}")
    public void the_user_enters_password(String password) {
        try {
            registerPage.enterPassword(password);
            ExtentReportManager.getTest().log(Status.PASS, "Entered password");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter password");
            throw e;
        }
    }







    @And("clicks on the save button")
    public void clicks_on_the_save_button() throws InterruptedException {
        try {
            registerPage.clickSaveButton();
            ExtentReportManager.getTest().log(Status.PASS, "Clicked save button");

            // Add wait for processing
            Thread.sleep(3000);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to click save button");
            throw e;
        }
    }

    @Then("the user should be automatically logged in")
    public void the_user_should_be_logged_in() {
        try {
            boolean isLoggedIn = registerPage.isUserLoggedIn();
            String currentUrl = registerPage.getCurrentUrl();

            Assertions.assertTrue(
                    isLoggedIn,
                    "User was not automatically logged in after registration. Current URL: " + currentUrl
            );

            ExtentReportManager.getTest().log(Status.PASS,
                    "User successfully registered and automatically logged in");

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Auto-login validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying auto-login");
            throw e;
        }
    }

    @Then("the user should see the account page")
    public void the_user_should_see_account_page() {
        try {
            boolean isSuccessful = registerPage.isRegistrationSuccessful();

            Assertions.assertTrue(
                    isSuccessful,
                    "Registration was not successful - user not redirected to account page"
            );

            ExtentReportManager.getTest().log(Status.PASS,
                    "User successfully registered and redirected to account page");

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Account page validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying account page");
            throw e;
        }
    }

    @Then("the user should see a registration error message")
    public void the_user_should_see_registration_error_message() {
        try {
            String errorMsg = registerPage.getErrorMessage();

            Assertions.assertFalse(
                    errorMsg.isEmpty(),
                    "Expected an error message but none was displayed"
            );

            ExtentReportManager.getTest().log(Status.PASS,
                    "Registration error displayed: " + errorMsg);

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Error message validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying error message");
            throw e;
        }
    }

    @Then("the registration should succeed")
    public void the_registration_should_succeed() {
        try {
            boolean isLoggedIn = registerPage.isUserLoggedIn();

            Assertions.assertTrue(
                    isLoggedIn,
                    "User should be logged in - registration should have succeeded"
            );

            ExtentReportManager.getTest().log(Status.PASS,
                    "Registration succeeded as expected");

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Registration success validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying registration success");
            throw e;
        }
    }

    @Then("the registration should fail and show error")
    public void the_registration_should_fail_and_show_error() {
        try {
            boolean isLoggedIn = registerPage.isUserLoggedIn();

            Assertions.assertFalse(
                    isLoggedIn,
                    "User should NOT be logged in after failed registration"
            );

            // This is the expected behavior → test passes
            ExtentReportManager.getTest().log(Status.PASS, "Registration failed as expected (user not logged in)");

            // NOW: Take screenshot on SUCCESS for negative case – to prove the error was shown
            String screenshotPath = TestBase.captureScreenshot("FailedRegistration_ExpectedBehavior");
            String relativePath = "../screenshots/" + new File(screenshotPath).getName().replace("target/", "");

            ExtentReportManager.getTest().pass("Error state captured",
                    MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());

            // Also log the actual error message from alert (if any)
            String errorMsg = registerPage.getErrorMessage();
            if (!errorMsg.isEmpty()) {
                ExtentReportManager.getTest().info("Error message displayed: " + errorMsg);
            }

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Unexpected: Registration should have failed but user was logged in");
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error during negative registration validation");
            throw e;
        }
    }

    private void attachScreenshotOnFailure(String message) {
        try {
            String absolutePath = TestBase.captureScreenshot(
                    System.currentTimeMillis() + "_screenshot"
            );
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
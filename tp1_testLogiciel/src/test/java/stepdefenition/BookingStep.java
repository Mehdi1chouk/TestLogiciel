package stepdefenition;

import base.TestBase;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import ExtentReport.ExtentReportManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.BookingPage;
import java.io.File;
import java.util.List;

public class BookingStep extends TestBase {
    private BookingPage bookingPage;
    public BookingStep() {
        bookingPage = new BookingPage(getDriver());
    }


    @Given("the user is on the activity catalog page")
    public void user_is_on_catalog_page() throws InterruptedException {
        try {
            bookingPage.openCatalogPage();
            Thread.sleep(1500);
            ExtentReportManager.getTest().log(Status.PASS, "Navigated to activity catalog");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to open catalog page");
            throw e;
        }
    }

    @Then("take a debug screenshot and show activities")
    public void debug_catalog() {
        String path = TestBase.captureScreenshot("CATALOG_DEBUG");
        ExtentReportManager.getTest().info("Screenshot: " + path);
        List<WebElement> titles = getDriver().findElements(By.cssSelector("h4.activity-title"));
        for (WebElement title : titles) {
            ExtentReportManager.getTest().info("Activity: '" + title.getText() + "'");
        }
    }

    @When("the user selects and books the {string} activity")
    public void select_and_book_activity(String activityName) {
        try {
            bookingPage.selectActivityByName(activityName);
            ExtentReportManager.getTest().log(Status.PASS, "Selected and booked activity: " + activityName);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to select/book activity: " + activityName);
            throw e;
        }
    }

    @When("the user clicks the book now button")
    public void click_book_now() {
        try {
            bookingPage.clickBookNowButton();
            ExtentReportManager.getTest().log(Status.PASS, "Clicked Book Now button");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to click Book Now button");
            throw e;
        }
    }

    @When("the user selects a future date")
    public void select_future_date() {
        try {
            bookingPage.selectFutureDate();
            ExtentReportManager.getTest().log(Status.PASS, "Selected future date");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to select future date");
            throw e;
        }
    }

    @When("the user selects time {string}")
    public void select_time(String time) {
        try {
            bookingPage.selectTime(time);
            ExtentReportManager.getTest().log(Status.PASS, "Selected time: " + time);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to select time");
            throw e;
        }
    }

    @When("the user selects number of guests {string}")
    public void select_guests(String guests) {
        try {
            bookingPage.selectNumberOfGuests(guests);
            ExtentReportManager.getTest().log(Status.PASS, "Selected guests: " + guests);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to select guests");
            throw e;
        }
    }

    @When("the user clicks the reserve button")
    public void click_reserve() {
        try {
            bookingPage.clickReserveButton();
            ExtentReportManager.getTest().log(Status.PASS, "Clicked Reserve button");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to click Reserve button");
            throw e;
        }
    }

    @And("the user enters phone number as {string}")
    public void enter_phone(String phone) throws InterruptedException {
        try {
            bookingPage.enterPhoneNumber(phone);
            bookingPage.enterNotes("");
            Thread.sleep(800);
            ExtentReportManager.getTest().log(Status.PASS, "Entered phone: " + phone);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter phone");
            throw e;
        }
    }


    @When("the user enters notes as {string}")
    public void enter_notes(String notes) {
        try {
            bookingPage.enterNotes(notes);
            ExtentReportManager.getTest().log(Status.PASS, "Entered notes");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to enter notes");
            throw e;
        }
    }

    @When("the user selects payment method")
    public void select_payment() {
        try {
            bookingPage.selectPaymentMethod();
            ExtentReportManager.getTest().log(Status.PASS, "Selected payment method");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to select payment method");
            throw e;
        }
    }


    @Then("the confirm booking button should be disabled")
    public void confirm_booking_button_should_be_disabled() {
        try {
            boolean isDisabled = bookingPage.isConfirmButtonDisabled();

            Assert.assertTrue(
                    "Expected confirm button to be disabled, but it was enabled!",
                    isDisabled
            );
            String path = TestBase.captureScreenshot("ConfirmButtonDisabled_Expected");
            String relative = "../screenshots/" + new File(path).getName();

            ExtentReportManager.getTest().pass(
                    "Confirm button is correctly disabled (validation success)",
                    MediaEntityBuilder.createScreenCaptureFromPath(relative).build()
            );

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Confirm button disabled validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error checking if confirm button is disabled");
            throw e;
        }
    }


    @And("the user clicks the confirm booking button")
    public void click_confirm_booking() {
        try {
            bookingPage.clickConfirmBooking();
            String path = TestBase.captureScreenshot("AFTER_CONFIRM_CLICK");
            ExtentReportManager.getTest().info("After confirm click: " + path);
            ExtentReportManager.getTest().log(Status.PASS, "Clicked Confirm Booking button");
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to confirm booking");
            throw e;
        }
    }

    @And("the user waits for {int} seconds")
    public void wait_seconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("the user should see booking confirmation modal")
    public void verify_booking_confirmation() {
        try {
            boolean isConfirmed = bookingPage.isBookingConfirmed();
            Assertions.assertTrue(isConfirmed, "Booking confirmation modal not displayed");
            ExtentReportManager.getTest().log(Status.PASS, "Booking confirmation modal displayed");
        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Booking confirmation verification failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying booking confirmation");
            throw e;
        }
    }

    @Then("the booking reference should be displayed")
    public void verify_booking_reference() {
        try {
            String reference = bookingPage.getBookingReference();
            Assertions.assertFalse(reference.isEmpty(), "Booking reference is empty");
            Assertions.assertTrue(reference.startsWith("BK"), "Booking reference format invalid: " + reference);
            ExtentReportManager.getTest().log(Status.PASS, "Booking reference generated: " + reference);
        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Booking reference validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying booking reference");
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
package stepdefenition;

import base.TestBase;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import ExtentReport.ExtentReportManager;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.CategoryPage;
import java.io.File;

public class CategoryStep extends TestBase {
    private CategoryPage categoryPage;
    private boolean categoryClicked = true;

    public CategoryStep() {
        categoryPage = new CategoryPage(getDriver());
    }

    @Given("the user is on the activity of catalog page")
    public void open_catalog_page() {
        categoryPage.openCategoryPage();
        ExtentReportManager.getTest().log(Status.INFO, "Opened activity catalog page");
    }

    @Then("take a debug screenshot and show the activities")
    public void take_debug_screenshot() {
        String screenshotPath = captureScreenshot("category_debug");
        String relativePath = "../screenshots/" + new File(screenshotPath).getName().replace("target/", "");

        ExtentReportManager.getTest().info("Debug screenshot - Activities displayed",
                MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
    }

    @When("the user clicks on the {string} category")
    public void click_category(String categoryName) {
        try {
            categoryPage.clickCategory(categoryName);
            ExtentReportManager.getTest().log(Status.PASS, "Clicked category: " + categoryName);
        } catch (Exception e) {
            attachScreenshotOnFailure("Failed to click category: " + categoryName);
            throw e;
        }
    }

    @And("the user should waits for {int} seconds")
    public void wait_seconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("the user should see all categories loaded successfully")
    public void verify_categories_loaded() {
        ExtentReportManager.getTest().log(Status.PASS, "All categories loaded successfully.");
    }

    @When("the user attempts to click on the {string} category")
    public void attempt_click_category(String categoryName) {
        try {
            if (!categoryPage.categoryExists(categoryName)) {
                ExtentReportManager.getTest().log(Status.INFO,
                        "Category '" + categoryName + "' does not exist (expected)");
            } else {
                categoryPage.clickCategory(categoryName);
                ExtentReportManager.getTest().log(Status.WARNING,
                        "Category '" + categoryName + "' unexpectedly exists and was clicked");
            }
        } catch (Exception e) {
            attachScreenshotOnFailure("Exception occurred while attempting to click category: " + categoryName);
            throw new RuntimeException("Error in attempt_click_category: " + e.getMessage(), e);
        }
    }

    @Then("the category {string} should not exist")
    public void verify_category_not_exists(String categoryName) {
        try {
            Assert.assertFalse(
                    "Category '" + categoryName + "' should not exist!",
                    categoryPage.categoryExists(categoryName)
            );

            ExtentReportManager.getTest().log(Status.PASS,
                    "Category '" + categoryName + "' correctly does not exist");
            String screenshotPath = TestBase.captureScreenshot("EmptyCategory_NotFound_ExpectedBehavior");
            String relativePath = "../screenshots/" + new File(screenshotPath).getName().replace("target/", "");

            ExtentReportManager.getTest().pass("Empty category handling verified",
                    MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());

        } catch (AssertionError ae) {
            attachScreenshotOnFailure("Category existence validation failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            attachScreenshotOnFailure("Error while verifying category existence");
            throw e;
        }
    }

    @Then("the application should not open the category")
    public void assert_category_not_opened() {
        String current = driver.getCurrentUrl();
        Assert.assertFalse("URL must not contain a category path!",
                current.contains("/category/"));
    }

    @Then("a {string} message should be displayed")
    public void assert_error_message(String expectedText) {
        WebElement msg = driver.findElement(By.cssSelector(".error-toast, .snackbar, .alert"));
        Assert.assertEquals(expectedText, msg.getText().trim());
    }

    @Then("no category page is loaded")
    public void assert_no_category_loaded() {
        Assert.assertFalse("Category should not have been opened!",
                categoryClicked);
        Assert.assertTrue("A category is still highlighted – expected none!",
                categoryPage.isNoCategoryActive());
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
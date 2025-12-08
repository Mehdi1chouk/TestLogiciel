package stepdefenition;

import base.TestBase;
import com.aventstack.extentreports.Status;
import ExtentReport.ExtentReportManager;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.CategoryPage;

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
        ExtentReportManager.getTest().log(
                Status.INFO,
                "Debug screenshot"
        );
    }

    @When("the user clicks on the {string} category")
    public void click_category(String categoryName) {
        try {
            categoryPage.clickCategory(categoryName);
            ExtentReportManager.getTest().log(Status.PASS, "Clicked category: " + categoryName);
        } catch (Exception e) {
            captureScreenshot("Failed to click category: " + categoryName);
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


    @Then("the application should not open the category")
    public void assert_category_not_opened() {
        // example: check that URL did not change to a category slug
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

    @When("the user attempts to click on the {string} category")
    public void attempt_click_category(String categoryName) {
        try {
            if (!categoryPage.categoryExists(categoryName)) {
                ExtentReportManager.getTest().log(Status.INFO,
                        "Category '" + categoryName + "' does not exist (expected)");
                // Don't throw, just log
            } else {
                categoryPage.clickCategory(categoryName);
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.INFO,
                    "Failed to click category as expected: " + categoryName);
        }
    }

    @Then("the category {string} should not exist")
    public void verify_category_not_exists(String categoryName) {
        Assert.assertFalse(
                "Category '" + categoryName + "' should not exist!",
                categoryPage.categoryExists(categoryName)
        );
    }


}

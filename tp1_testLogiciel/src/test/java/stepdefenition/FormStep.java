package stepdefenition;

import base.TestBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.FormPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormStep extends TestBase {
   /* @Test
    void form() {
        FormPage formPage = new FormPage(driver);

        formPage.openLoginPage();
        formPage.TextInput("eee");
        formPage.enterPassword("123456");
        formPage.TextArea("exemple");
        formPage.select(2);
        formPage.datalist(2);
        formPage.fileinput("aa.png");
        formPage.checkFirstCheckbox();
        formPage.checkSecondCheckbox();
        formPage.selectRadioButton();
        formPage.datepicker("2025-10-10");
        formPage.setRangeValue("8");

        formPage.clickSubmitButton();

        // ✅ Wait for the confirmation page to load
        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );
        String confirmationText = confirmation.getText();
        assertTrue(confirmationText.contains("Form submitted"), "Form should be submitted successfully");

        WebElement receivedText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("p"))
        );
        assertEquals("Received!", receivedText.getText(), "Form submission confirmation text should match exactly");


    }*/

    private FormPage formPage;

    public FormStep() {

        formPage = new FormPage(getDriver());
    }

    @Given("I open the web form page")
    public void i_open_the_web_form_page() {
        formPage = new FormPage(getDriver());
        formPage.openLoginPage();
    }

    @When("I enter {string} in the text field")
    public void i_enter_in_the_text_field(String text) {
        formPage.TextInput(text);
    }

    @When("I enter {string} in the password field")
    public void i_enter_in_the_password_field(String password) {
        formPage.enterPassword(password);
    }

//    @When("I enter {string} in the text area")
//    public void i_enter_in_the_text_area(String text) {
//        formPage.TextArea(text);
//    }
//
//    @When("I select option {int} from the select box")
//    public void i_select_option_from_the_select_box(Integer index) {
//        formPage.select(index);
//    }
//
//    @When("I choose option {int} from the datalist")
//    public void i_choose_option_from_the_datalist(Integer index) {
//        formPage.datalist(index);
//    }
//
//    @When("I select the date {string}")
//    public void i_select_the_date(String date) {
//        formPage.datepicker("string");
//    }
//
//    @When("I click on the submit button")
//    public void i_click_on_the_submit_button() {
//        formPage.clickSubmitButton();
//    }
//
//    @Then("the form should be submitted successfully")
//    public void the_form_should_be_submitted_successfully() {
//        formPage.clickSubmitButton();
//    }






}

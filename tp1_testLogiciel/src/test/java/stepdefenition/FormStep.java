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


}

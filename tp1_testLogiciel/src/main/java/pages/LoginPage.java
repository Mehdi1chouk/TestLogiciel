package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String LOGIN_URL = "http://localhost:4200/login";

    // Use name attributes since Angular generates dynamic ids
    private static final By EMAIL_LOCATOR = By.cssSelector("input[name='email']");
    private static final By PASSWORD_LOCATOR = By.cssSelector("input[name='password']");
    private static final By LOGIN_BUTTON_LOCATOR = By.cssSelector("button.login-btn");

    // Error message on Angular form
    private static final By ERROR_MESSAGE_LOCATOR = By.cssSelector(".error-message, .alert-danger");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openLoginPage() {
        driver.get(LOGIN_URL);
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_LOCATOR));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_LOCATOR));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON_LOCATOR));
        loginButton.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    // After login, Angular likely redirects to /books or /dashboard
    public boolean isRedirectedToHomePage() {
        try {
            return wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/books"),
                    ExpectedConditions.urlContains("/dashboard"),
                    ExpectedConditions.urlMatches("http://localhost:4200/?")
            ));
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            Alert alert = driver.switchTo().alert();
            String text = alert.getText();
            alert.accept();
            return text;
        } catch (Exception e) {
            return "";
        }
    }
}
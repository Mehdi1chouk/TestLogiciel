package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.Alert;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /*  NEW SITE – adjust host to your real Angular app  */
    private static final String REGISTER_DIRECT_URL = "http://localhost:4200/signup";

    /*  Locators matching the new DOM  */
    private static final By EMAIL_LOCATOR        = By.name("email");
    private static final By FIRST_NAME_LOCATOR   = By.name("firstName");
    private static final By LAST_NAME_LOCATOR    = By.name("lastName");
    private static final By PASSWORD_LOCATOR     = By.name("password");
    private static final By SUBMIT_BTN_LOCATOR   = By.cssSelector("button[type='submit'].signup-btn");

    /* Success / Error – adapt if your app shows something different */
    private static final By DASHBOARD_INDICATOR  = By.cssSelector("app-dashboard, .welcome, .user-menu"); // ← change if needed
    private static final By ERROR_MSG_LOCATOR    = By.cssSelector(".error-msg, .alert-danger, .mat-error"); // ← change if needed

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /* ---------- Navigation ---------- */
    public void openRegisterPage() {
        driver.get(REGISTER_DIRECT_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_LOCATOR));
    }

    /* ---------- Actions ---------- */
    public void enterEmail(String email) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_LOCATOR));
        el.clear();
        el.sendKeys(email);
    }

    public void enterFirstName(String firstName) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_LOCATOR));
        el.clear();
        el.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(LAST_NAME_LOCATOR));
        el.clear();
        el.sendKeys(lastName);
    }

    public void enterPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_LOCATOR));
        el.clear();
        el.sendKeys(password);
    }

    public void clickSaveButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BTN_LOCATOR));
        btn.click();

        // Wait briefly for alert or page action
        try { Thread.sleep(2000); } catch (InterruptedException ignore) {}
    }

    public boolean isRegistrationSuccessful() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert detected: " + alertText);
            alert.accept(); // close the alert
            return alertText.toLowerCase().contains("account created successfully");
        } catch (Exception e) {
            // No alert = not successful
            return false;
        }
    }

    public boolean isUserLoggedIn() {
        return isRegistrationSuccessful(); // same logic now
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



    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class FormPage {

    private WebDriver driver;
    private WebDriverWait wait;


    public FormPage(WebDriver driver){
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openLoginPage(){
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
    }

    public void TextInput(String username) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("my-text-id")));
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password){
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("my-password"))
        );
        passwordField.sendKeys(password);
    }

    public void TextArea(String username) {
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("my-textarea")));
        usernameField.sendKeys(username);
    }

    public void select(int index) {
        WebElement selectBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("my-select"))
        );

        Select dropdown = new Select(selectBox);
        dropdown.selectByIndex(index);
    }

    public void datalist(int index) {
        WebElement datalist = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("my-datalist"))
        );
        datalist.click();
    }

    public void fileinput(String filename) {
        // Construct a path to the file inside the assets directory
        File file = new File("src/main/resources/assets/" + filename);

        // Get the absolute path
        String absolutePath = file.getAbsolutePath();

        WebElement fileInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("my-file"))
        );

        // Send the full path to the input
        fileInput.sendKeys(absolutePath);
    }

    public void checkFirstCheckbox() {
        WebElement checkbox1 = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("my-check-1"))
        );
        if (!checkbox1.isSelected()) {
            checkbox1.click();
        }
    }

    public void checkSecondCheckbox() {
        WebElement checkbox2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("my-check-2"))
        );
        if (!checkbox2.isSelected()) {
            checkbox2.click();
        }
    }

    public void selectRadioButton() {
        WebElement radio1 = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("my-radio-1"))
        );
        if (!radio1.isSelected()) {
            radio1.click();
        }
    }




    public void datepicker(String date) {
        WebElement datepicker = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("my-date"))
        );
        datepicker.sendKeys(date);
    }

    public void setRangeValue(String value) {
        WebElement rangeInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("my-range"))
        );

        // Clear current value and set a new one (some browsers may not allow clear)
        rangeInput.sendKeys(value);
    }





    public void clickSubmitButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        button.click();
    }





}

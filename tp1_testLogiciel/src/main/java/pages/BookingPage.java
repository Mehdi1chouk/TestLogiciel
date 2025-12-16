package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BookingPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private static final String CATALOG_URL = "http://localhost:4200";
    private static final By ACTIVITY_CARD_LOCATOR = By.cssSelector("div.activity-card");
    private static final By ACTIVITY_TITLE_LOCATOR = By.cssSelector("h4.activity-title");
    private static final By BOOK_NOW_BUTTON_LOCATOR = By.cssSelector("button.book-btn");


    private static final By DATE_PICKER_LOCATOR = By.cssSelector("input[type='date']");
    private static final By TIME_DROPDOWN_LOCATOR = By.cssSelector("select.form-control");
    private static final By GUESTS_DROPDOWN_LOCATOR = By.cssSelector("select.form-control");
    private static final By RESERVE_BUTTON_LOCATOR = By.cssSelector("button.book-btn");


    private static final By PHONE_INPUT_LOCATOR = By.cssSelector("input[formcontrolname='phone']");
    private static final By NOTES_TEXTAREA_LOCATOR = By.cssSelector("textarea[formcontrolname='notes']");
    private static final By PAYMENT_RADIO_LOCATOR = By.cssSelector("input[value='card']");
    private static final By CONFIRMATION_MODAL_LOCATOR = By.xpath("//div[contains(@class, 'bg-white') and .//h3[contains(text(), 'Booking Confirmed!')]]");
    private static final By BOOKING_REFERENCE_LOCATOR = By.cssSelector("strong");
    private static final By CONFIRM_BUTTON_LOCATOR = By.xpath("//button[contains(., 'Confirm') or contains(., 'Réserver')]");


    public BookingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCatalogPage() {
        driver.get(CATALOG_URL);
    }


    public void clickBookNowButton() {
        List<WebElement> bookButtons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(BOOK_NOW_BUTTON_LOCATOR)
        );

        if (!bookButtons.isEmpty()) {
            WebElement firstButton = bookButtons.stream()
                    .filter(WebElement::isDisplayed)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No Book Now button found"));

            firstButton.click();
        } else {
            throw new RuntimeException("No Book Now buttons present on page");
        }
    }


    public void selectActivityByName(String activityName) {
        List<WebElement> cards = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(ACTIVITY_CARD_LOCATOR)
        );

        for (WebElement card : cards) {
            WebElement titleElement = card.findElement(ACTIVITY_TITLE_LOCATOR);
            String actualTitle = titleElement.getText().trim();

            if (actualTitle.equals(activityName)) {
                WebElement bookButton = card.findElement(BOOK_NOW_BUTTON_LOCATOR);
                wait.until(ExpectedConditions.elementToBeClickable(bookButton));
                bookButton.click();
                return;
            }
        }
        throw new RuntimeException("Activity '" + activityName + "' not found");
    }


    public void selectFutureDate() {
        WebElement datePicker = wait.until(ExpectedConditions.visibilityOfElementLocated(DATE_PICKER_LOCATOR));
        datePicker.clear();
        datePicker.sendKeys("2026-01-01");
        datePicker.click();
    }

    public void selectTime(String time) {
        List<WebElement> dropdowns = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(TIME_DROPDOWN_LOCATOR));
        Select select = new Select(dropdowns.get(0));
        select.selectByValue(time);
    }

    public void selectNumberOfGuests(String guests) {
        List<WebElement> dropdowns = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(GUESTS_DROPDOWN_LOCATOR));
        Select select = new Select(dropdowns.get(1));
        select.selectByValue(guests);
    }

    public void clickReserveButton() {
        WebElement reserveButton = wait.until(ExpectedConditions.elementToBeClickable(RESERVE_BUTTON_LOCATOR));
        reserveButton.click();
    }

    public void enterPhoneNumber(String phone) {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(PHONE_INPUT_LOCATOR));
        phoneInput.clear();
        phoneInput.sendKeys(phone);
        phoneInput.sendKeys(Keys.TAB);
    }

    public void enterNotes(String notes) {
        WebElement notesTextarea = wait.until(ExpectedConditions.visibilityOfElementLocated(NOTES_TEXTAREA_LOCATOR));
        notesTextarea.clear();
        notesTextarea.sendKeys(notes);
    }

    public void selectPaymentMethod() {
        WebElement paymentRadio = wait.until(ExpectedConditions.elementToBeClickable(PAYMENT_RADIO_LOCATOR));
        if (!paymentRadio.isSelected()) {
            paymentRadio.click();
        }
    }

    public void clickConfirmBooking() {
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(CONFIRM_BUTTON_LOCATOR));
        confirmButton.click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(CONFIRMATION_MODAL_LOCATOR),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3.text-lg")) // Generic wait for UI update
        ));
    }

    public boolean isBookingConfirmed() {
        try {
            WebElement confirmationModal = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(CONFIRMATION_MODAL_LOCATOR)
            );
            return confirmationModal.isDisplayed();
        } catch (Exception e) {
            System.out.println("DEBUG: Booking confirmation modal not found - " + e.getMessage());
            return false;
        }
    }

    public String getBookingReference() {
        try {
            WebElement referenceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(BOOKING_REFERENCE_LOCATOR));
            return referenceElement.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isConfirmButtonDisabled() {
        try {
            WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(CONFIRM_BUTTON_LOCATOR));
            String disabledAttr = btn.getAttribute("disabled");
            boolean isActuallyDisabled = disabledAttr != null || !btn.isEnabled();

            return isActuallyDisabled;
        } catch (Exception e) {
            return true;
        }
    }

}
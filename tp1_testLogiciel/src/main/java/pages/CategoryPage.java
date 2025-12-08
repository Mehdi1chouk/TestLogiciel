package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CategoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // 👉 URL of your Angular activity catalog page
    private static final String CATEGORY_URL = "http://localhost:4200/";

    // 👉 Selectors
    private static final By CATEGORY_TAB_LOCATOR = By.cssSelector("button.category-tab");
    private static final By CATEGORY_LABEL_LOCATOR = By.cssSelector("span.category-label");

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCategoryPage() {
        driver.get(CATEGORY_URL);
    }

    public void clickCategory(String categoryName) {
        List<WebElement> tabs = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(CATEGORY_TAB_LOCATOR)
        );

        for (WebElement tab : tabs) {
            String label = tab.findElement(CATEGORY_LABEL_LOCATOR).getText().trim();

            if (label.equalsIgnoreCase(categoryName)) {
                wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
                return;
            }
        }

        throw new RuntimeException("Category '" + categoryName + "' not found on UI");
    }



    public boolean isNoCategoryActive() {
        List<WebElement> tabs = driver.findElements(CATEGORY_TAB_LOCATOR);

        for (WebElement tab : tabs) {
            if (tab.getAttribute("class").contains("active")) {
                return false;
            }
        }
        return true;
    }

    public boolean categoryExists(String categoryName) {
        List<WebElement> tabs = driver.findElements(CATEGORY_TAB_LOCATOR);

        for (WebElement tab : tabs) {
            String label = tab.findElement(CATEGORY_LABEL_LOCATOR).getText().trim();
            if (label.equalsIgnoreCase(categoryName)) {
                return true;
            }
        }
        return false;
    }



}

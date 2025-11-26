package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestBase {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    // Removed @BeforeEach: Now a plain static method called by Hooks
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Removed @AfterEach: Now a plain static method called by Hooks
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {  // Made static for consistency (used in pages)
        return wait;
    }

    // Made static so Hooks can call without instantiation
    public static String captureScreenshot(String scenarioName) {
        try {
            // Vérifier que le driver peut prendre une capture
            TakesScreenshot ts = (TakesScreenshot) driver;

            // Capture d’écran en bytes
            byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);

            // Créer le dossier target/screenshots/ s’il n’existe pas
            File directory = new File("target/screenshots/");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Créer un horodatage unique
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));

            // Nom complet du fichier
            String fileName = scenarioName + "_" + timestamp + ".png";
            File screenshotFile = new File(directory, fileName);

            // Écrire les bytes dans un fichier
            try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
                fos.write(screenshotBytes);
            }

            // Afficher le chemin absolu dans la console
            String absolutePath = screenshotFile.getAbsolutePath();
            System.out.println("Capture enregistrée : " + absolutePath);

            return absolutePath;

        } catch (IOException | WebDriverException e) {
            System.err.println(" Erreur lors de la capture d’écran : " + e.getMessage());
            return "";
        }
    }
}
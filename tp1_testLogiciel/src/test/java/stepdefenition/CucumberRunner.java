package stepdefenition;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;  // Add this import
import org.junit.runner.RunWith;
import ExtentReport.ExtentReportManager;  // Add this import

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefenition",
        plugin = { "pretty", "html:target/cucumber-reports" },
        tags = "@CategoryNavigation"

)
public class CucumberRunner {

    @AfterClass  // This runs after ALL scenarios complete
    public static void flushReport() {
        ExtentReportManager.flush();
    }
}
package stepdefenition;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefenition",  // Fixed: Match your package name (lowercase, exact spelling)
        plugin = { "pretty", "html:target/cucumber-reports" }
)
public class CucumberRunner {
}
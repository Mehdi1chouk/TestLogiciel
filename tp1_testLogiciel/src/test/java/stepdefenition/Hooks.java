package stepdefenition;

import ExtentReport.ExtentReportManager;
import base.TestBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        if (TestBase.getDriver() == null) {
            TestBase.setUp();
        }
        ExtentReportManager.startTest(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        // Only log status, no screenshot here
        if (scenario.isFailed()) {
            ExtentReportManager.getTest().fail("Test Failed: " + scenario.getName());
        } else {
            ExtentReportManager.getTest().pass("Test Passed");
        }

        // Clean up ThreadLocal
        ExtentReportManager.removeTest();

        // Don't flush here - flush once at the end of execution
        // ExtentReportManager.flush(); // Move this to CucumberRunner or JVM shutdown hook

        TestBase.tearDown();
    }
}

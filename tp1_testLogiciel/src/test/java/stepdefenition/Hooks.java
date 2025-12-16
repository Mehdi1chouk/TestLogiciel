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
        if (scenario.isFailed()) {
            ExtentReportManager.getTest().fail("Test Failed: " + scenario.getName());
        } else {
            ExtentReportManager.getTest().pass("Test Passed");
        }
        ExtentReportManager.removeTest();
        TestBase.tearDown();
    }
}

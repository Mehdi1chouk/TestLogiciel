package ExtentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // ⭐ ADD THIS STATIC BLOCK ⭐
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (extent != null) {
                System.out.println("Flushing Extent Report on JVM shutdown...");
                extent.flush();
            }
        }));
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "target/Reports/ExtentReport_" + timestamp + ".html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setTheme(Theme.DARK);
            reporter.config().setDocumentTitle("Test Report - " + timestamp);

            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }

    public static synchronized ExtentTest getTest() {
        return extentTest.get();
    }

    public static synchronized void startTest(String testName) {
        ExtentTest test = getInstance().createTest(testName);
        extentTest.set(test);
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static synchronized void removeTest() {
        extentTest.remove();
    }
}
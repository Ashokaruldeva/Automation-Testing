package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportManager {
    
    private static Map<String, ExtentReports> extentReportsMap = new HashMap<>();
    private static Map<String, ExtentTest> extentTestMap = new HashMap<>();
    
    public static ExtentReports getExtentReports(String featureName) {
        if (!extentReportsMap.containsKey(featureName)) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/" + featureName + "/" + featureName + "_Report.html");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle(featureName + " Test Report");
            sparkReporter.config().setReportName(featureName + " Automation Results");
            
            ExtentReports extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Browser", "Chrome");
            
            extentReportsMap.put(featureName, extentReports);
        }
        return extentReportsMap.get(featureName);
    }
    
    public static ExtentTest createTest(String featureName, String scenarioName) {
        ExtentReports extentReports = getExtentReports(featureName);
        ExtentTest test = extentReports.createTest(scenarioName);
        extentTestMap.put(Thread.currentThread().getName(), test);
        return test;
    }
    
    public static ExtentTest getTest() {
        return extentTestMap.get(Thread.currentThread().getName());
    }
    
    public static void flushReport(String featureName) {
        if (extentReportsMap.containsKey(featureName)) {
            extentReportsMap.get(featureName).flush();
        }
    }
}
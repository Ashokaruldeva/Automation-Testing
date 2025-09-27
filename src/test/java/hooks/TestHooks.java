package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ExtentReportManager;
import utils.FeatureContext;
import com.aventstack.extentreports.ExtentTest;

public class TestHooks {
    
    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        String featureName = scenario.getUri().toString().replaceAll(".*/", "").replace(".feature", "");
        String scenarioName = scenario.getName();
        
        // Set feature name in ThreadLocal context
        FeatureContext.setFeatureName(featureName);
        
        // Create ExtentReports test
        ExtentReportManager.createTest(featureName, scenarioName);
    }
    
    @After
    public void afterScenario(Scenario scenario) {
        String featureName = scenario.getUri().toString().replaceAll(".*/", "").replace(".feature", "");
        ExtentTest test = ExtentReportManager.getTest();
        
        if (scenario.isFailed()) {
            test.fail("Scenario Failed: " + scenario.getName());
        } else {
            test.pass("Scenario Passed: " + scenario.getName());
        }
        
        ExtentReportManager.flushReport(featureName);
        
        // Clear ThreadLocal context
        FeatureContext.clear();
    }
}
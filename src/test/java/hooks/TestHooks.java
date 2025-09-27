package hooks;

import com.aventstack.extentreports.ExtentTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import tests.GenericSteps;
import utils.ExtentReportManager;

public class TestHooks {
    
    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        String featureName = scenario.getUri().toString().replaceAll(".*/", "").replace(".feature", "");
        String scenarioName = scenario.getName();
        
        // Set feature name first
        GenericSteps.setCurrentFeatureName(featureName);
        
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
    }
}
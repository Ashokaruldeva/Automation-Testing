package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ExtentReportManager;
import tests.GenericSteps;
import com.aventstack.extentreports.ExtentTest;

public class TestHooks {
    
    @Before
    public void beforeScenario(Scenario scenario) {
        String featureName = scenario.getUri().toString().replaceAll(".*/", "").replace(".feature", "");
        String scenarioName = scenario.getName();
        GenericSteps.setCurrentFeatureName(featureName);
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
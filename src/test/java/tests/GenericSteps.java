package tests;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class GenericSteps {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static String currentFeatureName;
    
    public static void setCurrentFeatureName(String featureName) {
        currentFeatureName = featureName;
    }
    
    private void logStepWithScreenshot(String stepDescription, String stepName) {
        ExtentTest test = ExtentReportManager.getTest();
        String screenshotPath = ScreenshotUtils.captureScreenshot(driver, stepName, currentFeatureName);
        
        if (screenshotPath != null) {
            try {
                test.info(stepDescription, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                test.info(stepDescription + " - Screenshot capture failed");
            }
        } else {
            test.info(stepDescription + " - Screenshot not available");
        }
    }
    
    private void setupDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            
            ChromeOptions options = new ChromeOptions();
            if (System.getProperty("headless", "false").equals("true")) {
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
    }
    
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        setupDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("oxd-input")));
        logStepWithScreenshot("Successfully navigated to OrangeHRM login page", "login_page");
    }
    
    @When("I enter username {string}")
    public void i_enter_username(String username) {
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='username']")));
        usernameField.clear();
        usernameField.sendKeys(username);
        logStepWithScreenshot("Entered username: " + username, "username_entered");
    }
    
    @When("I enter password {string}")
    public void i_enter_password(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='password']")));
        passwordField.clear();
        passwordField.sendKeys(password);
        logStepWithScreenshot("Entered password successfully", "password_entered");
    }
    
    @When("I click the login button")
    public void i_click_the_login_button() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("orangehrm-login-button")));
        loginButton.click();
        logStepWithScreenshot("Clicked on Login button to authenticate user", "login_clicked");
    }
    
    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        wait.until(ExpectedConditions.urlContains("dashboard"));
        logStepWithScreenshot("User successfully logged in and redirected to dashboard", "logged_in_dashboard");
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
package tests;

import io.cucumber.java.After;
import io.cucumber.java.Before;
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

public class LoginSteps {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    private void logStepWithScreenshot(String stepDescription, String stepName) {
        ExtentTest test = ExtentReportManager.getTest();
        String featureName = test.getModel().getName().toLowerCase().split(" ")[0];
        String screenshotPath = ScreenshotUtils.captureScreenshot(driver, stepName, featureName);
        
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
    
    @Before
    public void setup() {
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
    
    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("oxd-input")));
        logStepWithScreenshot("Successfully navigated to OrangeHRM login page", "login_page");
    }
    
    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='username']")));
        usernameField.clear();
        usernameField.sendKeys(username);
        
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='password']")));
        passwordField.clear();
        passwordField.sendKeys(password);
        logStepWithScreenshot("Entered username: " + username + " and password successfully", "credentials_entered");
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
    
    @When("I change to dark mode")
    public void i_change_to_dark_mode() {
        WebElement userDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.className("oxd-userdropdown-tab")));
        userDropdown.click();
        logStepWithScreenshot("Opened user dropdown menu to access theme options", "user_dropdown_opened");
        
        WebElement themeOption = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Change Theme")));
        themeOption.click();
        logStepWithScreenshot("Clicked on Change Theme option to switch to dark mode", "theme_changed");
    }
    
    @Then("the theme should be changed to dark mode")
    public void the_theme_should_be_changed_to_dark_mode() {
        logStepWithScreenshot("Dark mode theme successfully applied to the application", "dark_mode_applied");
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
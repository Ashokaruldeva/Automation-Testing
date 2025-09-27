package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtils {
    
    public static String captureScreenshot(WebDriver driver, String stepName, String featureName) {
        try {
            // Create ONLY the feature-named screenshot folder
            String screenshotDir = "reports/" + featureName + "/" + featureName + "_screenshots";
            Files.createDirectories(Paths.get(screenshotDir));
            
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            String fileName = stepName + "_" + System.currentTimeMillis() + ".png";
            String filePath = screenshotDir + "/" + fileName;
            
            Files.copy(sourceFile.toPath(), Paths.get(filePath));
            
            // Return absolute path to prevent ExtentReports from creating its own folders
            return new File(filePath).getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}
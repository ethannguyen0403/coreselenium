package com.paltech.utils;

import com.paltech.constant.Configs;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ScreenShotUtils {
    /**
     * Capturing an image and storing it in G:\
     *
     * @param driver         webdriver
     * @param screenshotName name of this images stored
     * @return imagePath
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        Map<String, String> env = System.getenv();
//        String imagePath = Configs.IMAGE_PATH.replace("file:", "");
//        String namePath = imagePath + screenshotName + ".png";
//        imagePath = String.format(namePath, env.get("COMPUTERNAME"));

        String imagePath = Configs.IMAGE_PATH + screenshotName + ".png";
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File(imagePath));
        } catch (IOException e) {
            System.out.println("Exception: Exception while taking screenshot " + e.getMessage());
        } catch (TimeoutException ex) {
            System.out.println("Exception: TimeoutException of the captureScreenshot " + ex.getMessage());
        }
        return imagePath;
    }

    public static String captureScreenshotWithBase64(WebDriver driver) {
        String srcBase64 = "";
        try {
            if (driver instanceof TakesScreenshot){
                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
                srcBase64 = takesScreenshot.getScreenshotAs(OutputType.BASE64);
            } else {
                System.out.println("Debug: Driver is not a TakesScreenshot instance");
            }
        } catch (TimeoutException ex) {
            System.out.println("Exception: TimeoutException of the captureScreenshotWithBase64 " + ex.getMessage());
        }
        return srcBase64;
    }
}

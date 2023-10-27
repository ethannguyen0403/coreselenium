package com.paltech.test;


import com.paltech.constant.CoreConstants;
import com.paltech.driver.DriverManager;
import com.paltech.driver.DriverProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Reporter.log;

public class isaTests {
    public static DriverProperties driverProperties;
    public static int getRandomInteger(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }


    @BeforeMethod(alwaysRun = true)
    public static void beforeMethod() throws Exception {

        System.out.println("***************************Beginning TC's *******************************");
        driverProperties = new DriverProperties();
        driverProperties.setMethodName("aaaa");
        driverProperties.setIsProxy(false);
        driverProperties.setElementWaitTimeOut(10);
        driverProperties.setBrowserName(CoreConstants.Browser.CHROME);
        driverProperties.setPlatform(CoreConstants.Platform.WINDOWS);
        driverProperties.setPlatformVersion("11");
        driverProperties.setExecutablePath("\\drivers\\chromedriver.exe");
        //driverProperties.setRemoteURL("http://10.42.2.27:5555");
        driverProperties.setRemoteURL("http://192.168.10.112:4444");
        int count = 3;
        DriverManager.quitAll();
        while (count-- > 0) {
            DriverManager.createWebDriver(driverProperties);
            DriverManager.getDriver().setLoadingTimeOut(100);
            DriverManager.getDriver().maximize();
            if (DriverManager.getDriver().getToAvoidTimeOut("https://www.saucedemo.com/") || count == 0) {
                log(String.format("RUNNING under the link %s", "https://www.saucedemo.com/"));
                log(String.format("DEBUG: CREATED DRIVER SUCCESSFULLY with COUNT %s and Map Size %s", count, DriverManager.driverMap.size()));
                System.out.print(String.format("Width x Height is %sx%s", DriverManager.getDriver().getWidth(), DriverManager.getDriver().getHeight()));
                break;
            } else {
                log("DEBUG: QUIT BROWSER DUE TO NOT CONNECTED");
                DriverManager.quitAll();
            }
        }
    }

    @Test(description = "Launch and Login")
    public void launchApp() throws Exception {
        try {
            DriverManager.getDriver().get("https://www.saucedemo.com/");
            DriverManager.getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
            DriverManager.getDriver().findElement(By.id("user-name")).sendKeys("secret_sauce");
            DriverManager.getDriver().findElement(By.id("user-name")).submit();
            DriverManager.getDriver().quit();

            System.out.println("Launch and Login - Done");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
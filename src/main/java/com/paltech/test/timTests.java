package com.paltech.test;


import com.paltech.constant.CoreConstants;
import com.paltech.driver.DriverManager;
import com.paltech.driver.DriverProperties;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Reporter.log;

public class timTests {
    public static DriverProperties driverProperties;
    public static int getRandomInteger(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }


    @BeforeMethod(alwaysRun = true)
    public static void beforeMethod() throws Exception {

        System.out.println("***************************Beginning TC's *******************************");
        driverProperties = new DriverProperties();
        driverProperties.setMethodName("Test Mobile");
        driverProperties.setRemoteURL("http://127.0.0.1:4723/wd/hub");
        driverProperties.setElementWaitTimeOut(10);
        driverProperties.setBrowserName(CoreConstants.Browser.CHROME);
        driverProperties.setPlatform(CoreConstants.Platform.ANDROID);
        driverProperties.setPlatformVersion("14");
        driverProperties.setUDID("emulator-5554");
        driverProperties.setAutonationName("UIAutomator2");
        driverProperties.setDeviceName("mobile_android34");
        int count = 3;
        DriverManager.quitAll();
        while (count-- > 0) {
            DriverManager.createWebDriver(driverProperties);
            DriverManager.getDriver().setLoadingTimeOut(100);
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
        By btnLogin = By.xpath("//button[@class='btn-in-out mr-2']");
        By txtUsername = By.name("username");
        By txtPassword = By.name("password");
        By btnLoginConfirm = By.xpath("//button[@class='btn-verification btn-confirm']");
        try {
            DriverManager.getDriver().get("https://www.fair999.com/x");
            DriverManager.getDriver().findElement(btnLogin).click();
            DriverManager.getDriver().findElement(txtUsername).sendKeys("qa2pl2");
            DriverManager.getDriver().findElement(txtPassword).sendKeys("Pal332211");
            DriverManager.getDriver().findElement(btnLoginConfirm).click();
            DriverManager.getDriver().quit();
            System.out.println("Launch and Login - Done");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
package com.paltech.driver;

import com.paltech.driver.browser.DesktopChromeDriver;
import com.paltech.driver.browser.DesktopFireFoxDriver;
import com.paltech.driver.browser.DesktopInternetExplorerDriver;
import com.paltech.driver.browser.DesktopSafariDriver;
import com.paltech.driver.cloud.SauceLabsDriver;
import com.paltech.driver.mobile.MobileAndroidDriver;
import com.paltech.driver.mobile.MobileIOSDriver;
import com.paltech.element.BaseElement;

import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverManager {
    public static Map<Integer, Driver> driverMap = new ConcurrentHashMap<Integer, Driver>();
    static Integer driverIndex = 0;
    public static Integer currentDriverIndex = 0;

    public static synchronized void storeWebDriver(Driver driver) {
        if (driverIndex == 0) {
            driverMap.put(driverIndex, driver);
        } else {
            driverMap.put(driverIndex, driver);
            DriverManager.currentDriverIndex = driverIndex;
        }
        DriverManager.driverIndex += 1;
    }

    public static synchronized Driver getDriver() {
        return driverMap.get(DriverManager.currentDriverIndex);
    }

    /**
     * Getting a driver following driverIndex within Map
     *
     * @param driverIndex the 1st driverIndex is 0, 2nd is 1
     * @return driver
     */
    public static synchronized Driver getDriver(Integer driverIndex) {
        try {
            if (driverIndex < 0 || driverIndex >= driverMap.size()) {
                System.err.println(String.format("ERROR: driverIndex is less than 0 or >= %s", driverMap.size()));
                return null;
            } else {
                DriverManager.currentDriverIndex = driverIndex;
                return driverMap.get(DriverManager.currentDriverIndex);
            }
        } catch (NullPointerException ex) {
            System.err.println("EXCEPTION: getDriver is " + ex);
            return null;
        }
    }

    /**
     * Activate a driver based on driverIndex within Map
     * @param driverIndex the 1st driverIndex is 0, 2nd is 1
     */
    public static synchronized void activateDriverWithIndex(Integer driverIndex) {
        try {
            if (driverIndex < 0 || driverIndex >= driverMap.size()) {
                System.err.println(String.format("ERROR: driverIndex is less than 0 or >= %s", driverMap.size()));
            } else {
                DriverManager.currentDriverIndex = driverIndex;
            }
        } catch (NullPointerException ex) {
            System.err.println("EXCEPTION: " + ex);
        }
    }

    /**
     * Quitting all drivers
     */
    public static synchronized void quitAll() {
        for (int i = driverMap.size()-1; i >=0; i--) {
            driverMap.get(i).quit();
            driverMap.remove(i);
        }
        driverIndex = 0;
        currentDriverIndex = 0;
    }

    public static synchronized void removeWebDriver(Integer driverIndex) {
        if (driverIndex < 0 || driverIndex >= driverMap.size()) {
            System.err.println(String.format("ERROR: driverIndex is less than 0 or >= %s", driverMap.size()));
            return;
        }
        driverMap.remove(DriverManager.currentDriverIndex);
        if (driverMap.size() > 0) {
            DriverManager.currentDriverIndex = 0;
        } else {
            System.out.println(String.format("DEBUG: currentDriverIndex is -1 because the size of driverMap is %s", driverMap.size()));
            DriverManager.currentDriverIndex = -1;
        }
    }

    public static synchronized void createWebDriver(DriverProperties driverProperties) throws MalformedURLException, UnexpectedException {
        Driver driver = null;
        System.out.println(String.format("[STEP] Init this driver on %s.", driverProperties.getPlatform()));

        if (driverProperties.getRemoteURL().equals("SAUCE-LABS")) {
            driver = new SauceLabsDriver(driverProperties);
        } else {
            switch (driverProperties.getPlatform()) {
                case IOS:
                case ANDROID:
                    driver = initAppiumDriver(driverProperties);
                    break;
                case LINUX:
                case MAC:
                case WINDOWS:
                default:
                    driver = initSeleniumDriver(driverProperties);
                    break;
            }
        }
        storeWebDriver(driver);
    }

    private static Driver initSeleniumDriver(DriverProperties driverProperties) throws MalformedURLException {
        System.out.println(String.format("[STEP] Init WebDriver for %s browser on %s.", driverProperties.getBrowserName(), driverProperties.getPlatform()));
        switch (driverProperties.getBrowserName()) {
            case FIREFOX:
                return new DesktopFireFoxDriver(driverProperties);
            case INTERNET_EXPLORER:
                return new DesktopInternetExplorerDriver(driverProperties);
            case CHROME:
                try{
                    return new DesktopChromeDriver(driverProperties);
                } catch (RuntimeException ex){
                    System.err.println("[ERROR]: RuntimeException at initSeleniumDriver and then retry to create the second time");
                    return new DesktopChromeDriver(driverProperties);
                }

            case SAFARI:
                return new DesktopSafariDriver(driverProperties);
            default:
                System.err.println(String.format("[ERROR]: Cannot create Local Local/Remote Web driver for browser %s on %s.\n", driverProperties.getBrowserName(), driverProperties.getPlatform()));
        }
        return null;
    }

    private static Driver initAppiumDriver(DriverProperties driverProperties) throws MalformedURLException {
        System.out.println(String.format("[STEP] Init Mobile driver for browser %s with remote URL %s.\n", driverProperties.getBrowserName(), driverProperties.getRemoteURL()));
        switch (driverProperties.getPlatform()) {
            case IOS:
                return new MobileIOSDriver(driverProperties);
            case ANDROID:
                return new MobileAndroidDriver(driverProperties);
            default:
                System.err.println(String.format("[ERROR]: Cannot create Mobile driver for browser %s with remote URL %s.\n", driverProperties.getBrowserName(), driverProperties.getRemoteURL()));
        }
        return null;
    }


}

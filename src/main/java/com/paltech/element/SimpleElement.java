package com.paltech.element;

import com.paltech.constant.StopWatch;
import com.paltech.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;


public class SimpleElement {
    //Private final Locator locator;
    private WebElement instance = null;
    private final By locator;
    private final int timeOutInSeconds = 10;
    public final int timeOutShortInSeconds = 5;

    //Private final Locator locator;
    public By getLocator() {
        return locator;
    }

    public SimpleElement(By locator) {
        this.locator = locator;
    }

    public WebElement getWebElement() {
        return getWebElement(true);
    }

    public WebElement getWebElement(boolean isLog) {
        synchronized (BaseElement.class) {
            instance = DriverManager.getDriver().findElement(locator, isLog);
        }
        return instance;
    }

    protected void reload() {
        instance = DriverManager.getDriver().findElement(locator);
    }

    protected <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return getWebElement().getScreenshotAs(target);
    }

    public void click() {
        try {
//            logStartAction(String.format("click: '%s'", locator));
            getWebElement().click();
            logEndAction(String.format("clicked: '%s'", locator));
        } catch (NullPointerException ex) {
            logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
        } catch (WebDriverException ex) {
            logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
        }
    }

    protected String getTagName() {
        try{
//            logStartAction(String.format("get Tag Name: '%s'", locator));
            String tagName = getWebElement().getTagName();
            logEndAction(String.format("got Tag Name value '%s' is %s", tagName, locator));
            return tagName;
        } catch (NullPointerException ex) {
            logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
            return "";
        } catch (WebDriverException ex) {
            logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
            return "";
        }
    }

    public String getAttribute(String name) {
        return getAttribute(name, true);
    }

    public String getAttribute(String name, boolean isLogged) {
        try{
//            if(isLogged){logStartAction(String.format("get '%s' attribute: %s", name, locator));}
            String value = getWebElement(false).getAttribute(name);
            if(isLogged){logEndAction(String.format("got attribute value '%s' is %s", value, locator));}
            return value;
        } catch (NullPointerException ex) {
            logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
            return "";
        } catch (WebDriverException ex) {
            logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
            return "";
        }
    }

    /**
     * Checking if this control is selected
     * @return boolean
     */
    public boolean isSelected() {
        try {
//            logStartAction(String.format("check if this control '%s' is selected", locator));
            boolean isSelected = getWebElement().isSelected();
            logEndAction(String.format("checked if this control '%s' selected is  %s", locator, isSelected));
            return isSelected;

        } catch (NullPointerException ex) {
            logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
            return false;
        } catch (WebDriverException ex) {
            logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
            return false;
        }
    }

    /**
     * Checking if this control is enabled
     * @return boolean
     */
    public boolean isEnabled() {
        try{
//            logStartAction(String.format("get if this control '%s' is enabled status", locator));
            boolean isEnabled = getWebElement().isEnabled();
            logEndAction(String.format("got if this control '%s' is enabled %s", isEnabled, locator));
            return isEnabled;
        } catch (NullPointerException ex){
            logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
            return false;
        } catch (WebDriverException ex) {
            logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
            return false;
        }
    }

    /**
     * Getting text of this control
     * @param timeOutInSeconds set how many seconds you expect that text is returned successfully
     * @return text
     */
    public String getText(int timeOutInSeconds) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
//        logStartAction(String.format("get text of this control '%s' after timeoutInSeconds %s", locator, timeOutInSeconds));
        String text = "";
        while (stopWatch.getElapsedTime() < (timeOutInSeconds*1000)){
            try{
                instance = this.getWebElement();
                if (instance != null){
                    text = instance.getText();
                    logEndAction(String.format("got text of this control '%s' is  %s", locator, text));
                    return text;
                }
                logEndAction(String.format("got text value of this control '%s'  is '%s'", locator,text));
                return text;
            } catch (StaleElementReferenceException ex) {
                logStartAction(String.format("StaleElementReferenceException: still waiting for this element '%s' presence", locator));
            } catch (TimeoutException ex) {
                logEndAction(String.format("Timeout: This element '%s' still displays after timeout %s", locator, timeOutInSeconds));
                break;
            } catch (Exception ex) {
                logEndAction(String.format("Exception: can not find element: '%s' after waiting for %s second(s)", locator, timeOutInSeconds));
            }
        }
        return text;
    }

    /**
     * Checking if this control is displayed
     * @param timeOutInSeconds how many seconds you want to wait
     * @return boolean
     */
    public boolean isDisplayed(int timeOutInSeconds) {
        try {
            logStartAction(String.format("check if this control is displayed after timeoutInSeconds %s by locator '%s'", timeOutInSeconds, locator));
            boolean isDisplayed = DriverManager.getDriver().isElementVisible(locator, timeOutInSeconds);
            logEndAction(String.format("checked if this control displayed is %s with xpath '%s'", isDisplayed, locator));
            return isDisplayed;
        }  catch (StaleElementReferenceException ex) {
//            logStartAction(String.format("Error: StaleElementReferenceException is still waiting for this element '%s'", locator));
            return false;
        } catch (NullPointerException ex) {
            logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
            return false;
        } catch (WebDriverException ex) {
            logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
            return false;
        }
    }

    /**
     * Checking if a control is invisible after period of time
     * author: liam.ho
     * @param timeoutInSeconds how many seconds you want to wait for this control
     * @return boolean
     */
    public boolean isInvisible(int timeoutInSeconds) {
        try {
//            logStartAction(String.format("check if this control is invisible after timeoutInSeconds %s by locator '%s'", timeoutInSeconds, locator));
            boolean isInvisible = DriverManager.getDriver().isElementInvisible(locator, timeoutInSeconds);
            logEndAction(String.format("checked if this control invisible is %s by locator '%s'", isInvisible, locator));
            return isInvisible;
        } catch (NullPointerException ex) {
            logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
            return false;
        } catch (WebDriverException ex) {
            logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
            return false;
        }
    }

    //Private area
    private void logStartAction(String msg) {
//        System.out.println(String.format("[Element]Start action: %s", msg));
    }

    private void logEndAction(String msg) {
//        System.out.println(String.format("[Element]Done action: %s", msg));
    }
}

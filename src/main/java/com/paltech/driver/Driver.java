package com.paltech.driver;
import com.paltech.constant.StopWatch;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
/**
 * @author isabella.huynh
 * created on Nov/9/2019.
 */
public class Driver implements WebDriver {
	protected WebDriver webDriver;
	protected DriverProperties driverProperties;

	public WebDriver getWebDriver() {
		return webDriver;
	}
	protected void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public DriverProperties getDriverSetting() {
		return driverProperties;
	}

	protected void setDriverSetting(DriverProperties properties) {
		this.driverProperties = properties;
	}

	public Driver(DriverProperties properties) {
		setDriverSetting(properties);
	}

	public void get(String url) {
		try {
			webDriver.get(url);
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException at get Driver method on Driver class");
			refresh();
		}

	}

	public boolean getToAvoidTimeOut(String url) {
		try {
			webDriver.get(url);
			return true;
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException at get Driver method on Driver class");
			return false;
		} catch (RuntimeException ex){
			System.out.println("Exception: RuntimeException at get Driver method on Driver class");
			return false;
		}
	}

	public String getCurrentUrl() {
		try{
			return webDriver.getCurrentUrl();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException while getting getCurrentUrl");
			return "";
		}
	}

	public String getTitle() {
		try {
			return webDriver.getTitle();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getTitle method");
			return "";
		}
	}

	/**
	 * Setting up loading of page to limit timeout. Default loading wait is 300 seconds
	 */
	public void setLoadingTimeOut(){
		setLoadingTimeOut(driverProperties.getElementWaitTimeOut());
	}

	public void setLoadingTimeOut(int timeOutSeconds){
		getWebDriver().manage().timeouts().pageLoadTimeout(timeOutSeconds, TimeUnit.SECONDS);
	}

	/**
	 * Finding elements and then return them
	 * @param by location
	 * @return WebElements
	 */
	public List<WebElement> findElements(By by) {
//		logStartAction(String.format("find elements: %s", by));
		WebDriverWait driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(driverProperties.getElementWaitTimeOut()));
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<WebElement> elems = null;
		while (stopWatch.getElapsedTime() < (driverProperties.getElementWaitTimeOut()*1000)) {
			try {
				elems = driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
				if (elems != null){
					logEndAction(String.format("found elements '%s'", by));
					return elems;
				}
			} catch (StaleElementReferenceException ex) {
				logStartAction(String.format("still finding for this element '%s'", by.toString()));
			} catch (TimeoutException ex) {
				logEndAction(String.format("can not find elements '%s'", by));
				break;
			} catch (Exception ex) {
				System.out.println(String.format("exception message is '%s'", ex.getMessage()));
			}
		}
		return elems;
	}

	/**
	 * Finding element and then return it
	 * @param by location
	 * @return WebElement
	 */
	public WebElement findElement(By by) {
		return findElement(by, true);
	}

	public WebElement findElement(By by, boolean isLog) {
//		if(isLog) {logStartAction(String.format("find element '%s'", by.toString()));}
		WebDriverWait driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(driverProperties.getElementWaitTimeOut()));
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		WebElement elem = null;
		while (stopWatch.getElapsedTime() < (driverProperties.getElementWaitTimeOut() * 500)) {
			try {
				elem = driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
				if(elem != null){
					if(isLog) {logEndAction(String.format("found element: %s", by.toString()));}
					return elem;
				}
			} catch (StaleElementReferenceException ex) {
				logStartAction(String.format("still finding for this element '%s'", by.toString()));
			} catch (TimeoutException ex) {
				logEndAction(String.format("Could not find element '%s'", by.toString()));
			} catch (Exception ex) {
				logEndAction(String.format("Error: Exception '%s'", ex.getMessage()));
			}
		}
		return elem;
	}

	/**
	 * Finding element and then return it
	 * @param by location
	 * @param timeOutSeconds how many seconds
	 * @return WebElement
	 */
	public WebElement findElement(By by, int timeOutSeconds) {
//		logStartAction(String.format("find element '%s'", by.toString()));
		WebDriverWait driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(driverProperties.getElementWaitTimeOut()));
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		WebElement elem = null;
		while (stopWatch.getElapsedTime() < (timeOutSeconds * 1000)) {
			try {
				elem = driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
				if(elem != null){
					logEndAction(String.format("found element: %s", by.toString()));
					return elem;
				}
			} catch (StaleElementReferenceException ex) {
				logStartAction(String.format("still finding for this element '%s'", by.toString()));
			} catch (TimeoutException ex) {
				logEndAction(String.format("Could not find element '%s'", by.toString()));
				break;
			} catch (Exception ex) {
				System.out.println(String.format("Error: Exception '%s'", ex.getMessage()));
			}
		}
		return elem;
	}

	/**
	 * Checking if a control is invisible after period of time
	 * author: isabella.huynh
	 * @param by locator
	 * @param timeout how many seconds you want to wait for this control
	 * @return boolean
	 */
	public boolean isElementInvisible(By by, int timeout){
//		logStartAction(String.format("Waiting an element '%s' invisible within timeout %s", by, timeout));
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		WebDriverWait driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(timeout));
		while (stopWatch.getElapsedTime() < (timeout*1000)){
			try {
				Boolean isInvisible = driverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
				if (isInvisible){
					logEndAction(String.format("Debug: This element is invisible  '%s' after timeout %s", by, timeout));
					return true;
				}
			} catch (StaleElementReferenceException ex) {
				logStartAction(String.format("still finding for this element '%s'", by.toString()));
			} catch (TimeoutException ex) {
				logEndAction(String.format("Exception: This element '%s' still displays after timeout %s", by, timeout));
				break;
			} catch (Exception ex) {
				System.out.println("Error: An exception: " + ex.getMessage());
			}
		}
		logEndAction(String.format("Debug: This element isn't invisible '%s' after timeout %s", by, timeout));
		return false;
	}

	/**
	 * Finding element and then return it if it is presence
	 * @param by location
	 * @param timeoutInSeconds how many seconds you will wait fo
	 * @return WebElement
	 */
	public WebElement findElementPresence(By by, int timeoutInSeconds) {
//		logStartAction(String.format("find element: %s", by.toString()));
		WebDriverWait driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds));
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		WebElement elem = null;

		logEndAction(String.format("find element '%s'", by.toString()));
		while (stopWatch.getElapsedTime() < (timeoutInSeconds * 1000)) {
			try {
				elem = driverWait.until(ExpectedConditions.presenceOfElementLocated(by));
				if (elem != null) break;
			} catch (StaleElementReferenceException ex) {
				logStartAction(String.format("still waiting for this element '%s' presence", by.toString()));
			} catch (TimeoutException ex) {
				logEndAction(String.format("can not find element: %s", by.toString()));
				break;
			} catch (Exception ex) {
				System.out.println(String.format("Error: Exception '%s'", ex.getMessage()));
			}
		}
		return elem;
	}

	public List<WebElement> findElementPresences(By by) {
//		logStartAction(String.format("find elements: %s", by));
		WebDriverWait driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(driverProperties.getElementWaitTimeOut()));
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<WebElement> lstElements = null;
		while (stopWatch.getElapsedTime() < (driverProperties.getElementWaitTimeOut()*1000)) {
			try {
				lstElements = driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
				if (lstElements != null){
					logEndAction(String.format("found elements '%s'", by));
					return lstElements;
				}
			} catch (StaleElementReferenceException ex) {
				logStartAction(String.format("still finding for this element '%s'", by.toString()));
			} catch (TimeoutException ex) {
				logEndAction(String.format("can not find elements '%s'", by));
				break;
			} catch (Exception ex) {
				System.out.println(String.format("exception message is '%s'", ex.getMessage()));
			}
		}
		return lstElements;
	}


	/**
	 * Checking if a control visible after period of time
	 * author: liam.ho
	 * @param by locator
	 * @param timeout how many seconds you want to wait for this control
	 * @return boolean
	 */
	public boolean isElementVisible(By by, int timeout){
//		logStartAction(String.format("Waiting an element '%s' visible within timeout %s", by, timeout));
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		WebDriverWait driverWait = new WebDriverWait(webDriver,Duration.ofSeconds( timeout));
		WebElement element = null;
		while (stopWatch.getElapsedTime() < (timeout*1000)){
			try {
				element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
				if (element != null){
					logEndAction(String.format("Debug: This element is visible  '%s' after timeout %s", by, timeout));
					return true;
				}
			} catch (StaleElementReferenceException ex) {
				logStartAction(String.format("still finding for this element '%s'", by.toString()));
			} catch (TimeoutException ex) {
				logEndAction(String.format("Exception: This element '%s' still displays after timeout %s", by, timeout));
			} catch (Exception ex) {
				System.out.println("Error: An exception: " + ex.getMessage());
			}
		}
		if (element == null){
			return false;
		}
		logEndAction(String.format("Debug: This element isn't invisible  '%s' after timeout %s", by, timeout));
		return false;
	}

	/**
	 * Checking if a control is clickable after period of time
	 * @param by locator
	 * @param timeout how many seconds you can click on this control
	 * @return boolean
	 */
	public boolean isElementClickable(By by, int timeout){
//		logStartAction(String.format("Waiting for an element '%s' is clickable within timeout %s", by, timeout));
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		WebDriverWait driverWait = new WebDriverWait(webDriver, Duration.ofSeconds(timeout));
		WebElement element = null;
		while (stopWatch.getElapsedTime() < (timeout*1000)){
			try {
				element = driverWait.until(ExpectedConditions.elementToBeClickable(by));
				if (element != null){
					logEndAction(String.format("Debug: This element is visible  '%s' after timeout %s", by, timeout));
					return true;
				}
			} catch (StaleElementReferenceException ex) {
				logStartAction(String.format("still finding for this element '%s'", by.toString()));
			} catch (TimeoutException ex) {
				logEndAction(String.format("Exception: This element '%s' still displays after timeout %s", by, timeout));
			} catch (Exception ex) {
				System.out.println("Error: An exception: " + ex.getMessage());
			}
		}
		if (element == null){
			return false;
		}
		logEndAction(String.format("Debug: This element isn't invisible  '%s' after timeout %s", by, timeout));
		return false;
	}


	public String getPageSource() {
		logEndAction("get Page Source");
		return webDriver.getPageSource();
	}

	public void close() {
//		logStartAction("close Window");
		webDriver.close();
		logEndAction("closed Window");
	}

	public void quit() {
//		logStartAction("quit browser");
		webDriver.quit();
		logEndAction("quited browser");
	}

	public Set<Cookie> getCookies(){
//		logStartAction("get cookies");
		logEndAction("got cookies");
		return this.webDriver.manage().getCookies();
	}

	public void active(){
//		logStartAction("activate");
		logEndAction("activated");
	}

	public void addCookie(Cookie cookie){
		this.webDriver.manage().addCookie(cookie);
	}

	public void deleteAllCookies(){
//		logStartAction("delete cookies");
		this.webDriver.manage().deleteAllCookies();
		logEndAction("deleted cookies");
	}

	public void deleteCookie(String cookieName){
//		logStartAction(String.format("delete a cookie name '%s'", cookieName));
		this.webDriver.manage().deleteCookieNamed(cookieName);
		logEndAction("deleted this cookie");
	}

	public void back(){
		try {
//			logStartAction("click back button on browser");
			this.webDriver.navigate().back();
			logEndAction("clicked back button on browser");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of BACK method");
		}

	}

	public void refresh(){
		try {
//			logStartAction("refresh icon on browser");
			this.webDriver.navigate().refresh();
			logEndAction("refreshed icon on browser");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of REFRESH method");
		}

	}

	public void forward(){
		try {
			logStartAction("forward icon on browser");
			this.webDriver.navigate().forward();
			logEndAction("forwarded icon on browser");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of FORWARD method");
		}
	}

	public void switchToFrame(String frameName){
		try {
			logStartAction(String.format("switch to frame name '%s'", frameName));
			this.webDriver.switchTo().frame(frameName);
			logStartAction(String.format("switched to frame name '%s'", frameName));
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of switchToFrame method");
		}
	}

	public void switchToFrame(int frameID){
		try {
			logStartAction(String.format("switch to frame id '%s'", frameID));
			this.webDriver.switchTo().frame(frameID);
			logStartAction(String.format("switched to frame id '%s'", frameID));
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of SWITCHTOFRAME method");
		}

	}

	public void switchToFrame(WebElement e){
		try {

			logStartAction(String.format("switch to frame name '%s'", e.getLocation()));
			this.webDriver.switchTo().frame(e);
			logStartAction(String.format("switched to frame name %s", "successfully"));
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of switchToFrame method");
		}
	}

	public void switchToDefaultContent(){
		try {
			logStartAction("switch to default content");
			this.webDriver.switchTo().defaultContent();
			logStartAction("switched to default content");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of switchToDefaultContent");
		}
	}

	public void switchToParentFrame(){
		try {
			logStartAction("switch to parent frame");
			this.webDriver.switchTo().parentFrame();
			logStartAction("switched to parent frame");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of switchToParentFrame");
		}
	}

	/**
	 * getting Alert handle
	 * @return Alert
	 */
	public Alert switchToAlert(){
		try {
			logStartAction("switch to Alert popup");
			logStartAction("switched to Alert popup");
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(3));
			wait.until(ExpectedConditions.alertIsPresent());
			return this.getWebDriver().switchTo().alert();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of SWITCHTOALERT method");
			return null;
		}
	}

	public String getAlertMessage(){
		try {
			logStartAction("get Alert message");
			Alert alert = switchToAlert();
			logStartAction("got Alert message");
			return alert.getText();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of GET ALERT MESSAGE method");
			return "";
		}
	}

	public void clickAlertAccept(){
		try {
			logStartAction("click Accept button on Alert");
			Alert alert = switchToAlert();
			alert.accept();
			logStartAction("clicked Accept button on Alert");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of clickAlertAccept method");
		}
	}

	public void clickAlertDismiss(){
		try {
			logStartAction("click Dismiss button on Alert");
			Alert alert = switchToAlert();
			alert.dismiss();
			logStartAction("clicked Dismiss button on Alert");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of clickAlertDismiss method");
		}
	}


	public TargetLocator switchTo() {
		try {
			return webDriver.switchTo();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of switchTo method");
			return null;
		}
	}

	public Set<String> getWindowHandles() {
		try {
			return webDriver.getWindowHandles();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getWindowHandles method");
			return null;
		}
	}

	public String getWindowHandle() {
		try {
			return webDriver.getWindowHandle();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getWindowHandle method");
			return "";
		}
	}

	public void switchToWindow(String windowName){
		try {
			DriverManager.getDriver().switchTo().window(windowName);
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of switchToWindow method");
		}
	}

	/**
	 * To switch to a new handle opened but there is only 2 handles
	 */
	public void switchToWindow(){
		try {
			String parent = DriverManager.getDriver().getWindowHandle();
			Set<String> handles = DriverManager.getDriver().getWindowHandles();
			if (handles.size() >= 2) {
				for (String handle : handles) {
					if (!handle.equals(parent)) {
						DriverManager.getDriver().switchToWindow(handle);
						break;
					}
				}
			}
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of switchToWindow method");
		}
	}

	public int getWindowHeight(){
		logStartAction("getting window height");
		try {
			JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver().getWebDriver();
			logEndAction("got window height");
			return (int)jse.executeScript("return document.body.scrollHeight");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getWindowHeight method");
		} catch (RuntimeException ex){
			System.out.println("Exception: RuntimeException of getWindowWidth method");
		}
		return -1;
	}

	public int getWidth() {
		try {
			return DriverManager.getDriver().getWebDriver().manage().window().getSize().getWidth();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getWindowHeight method");
		} catch (RuntimeException ex){
			System.out.println("Exception: RuntimeException of getWindowWidth method");
		}
		return -1;

	}

	public int getHeight() {
		try {
			return DriverManager.getDriver().getWebDriver().manage().window().getSize().getHeight();
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getWindowHeight method");
		} catch (RuntimeException ex){
			System.out.println("Exception: RuntimeException of getWindowWidth method");
		}
		return -1;
	}

	public int getWindowWidth(){
		logStartAction("getting window width");
		try {
			JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver().getWebDriver();
			logEndAction("got window width");
			return (int)jse.executeScript("return document.body.scrollWidth");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getWindowWidth method");
		} catch (RuntimeException ex){
			System.out.println("Exception: RuntimeException of getWindowWidth method");
		}
		return -1;
	}

	public void scrollToBottom(){
		logStartAction("scroll to bottom");
		try {
			JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver().getWebDriver();
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			logEndAction("scrolled to bottom");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getWindowWidth method");
		} catch (RuntimeException ex){
			System.out.println("Exception: RuntimeException of getWindowWidth method");
		}
	}

	public void scrollTo(int width, int height){
		logStartAction("scroll to location " + height);
		try {
			JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver().getWebDriver();
			jse.executeScript(String.format("window.scrollTo(%s, %s)", width, height));
			logEndAction("scrolled to height" + height);
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of scrollTo method");
		}
	}

	public long getScrollX(){
		logStartAction("get scroll X");
		try {
			JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver().getWebDriver();
			logEndAction("got scroll X");
			return (long)jse.executeScript("return window.scrollX");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getScrollX method");
		}
		return -1;
	}

	public long getScrollY(){
		logStartAction("get scroll Y");
		try {
			JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver().getWebDriver();
			logEndAction("got scroll Y");
			return (long)jse.executeScript("return window.scrollY");
		} catch (TimeoutException ex){
			System.out.println("Exception: TimeoutException of getScrollY method");
		}
		return -1;
	}

	public void getScreenShot(String fileName, String path){
		logStartAction("get Screenshot");
		try {
			String fullPath = String.format("%s%s.png", path, fileName);
			TakesScreenshot screenshot = ((TakesScreenshot) DriverManager.getDriver().webDriver);
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			File destFile=new File(fullPath);
			FileUtils.copyFile(srcFile, destFile);
		} catch (Exception ex){
			System.err.println("Exception: TimeoutException or IOException occurs at getScreenShot method");
		}
	}

	public Navigation navigate() {
		return webDriver.navigate();
	}

	public Options manage() {
		return webDriver.manage();
	}

	public void maximize() {
		webDriver.manage().window().maximize();
	}

	public void waitForElement(WebElement element, int timeoutSeconds){
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void newTab() {
		this.executeJavascript("window.open()");
	}

	/**
	 * Executing a command of javascript
	 * @param element WebElement
	 * @param javascript "arguments[0].click();"
	 */
	public void executeJavascript(WebElement element, String javascript){
		if (Objects.isNull(element)) {
			logStartAction("executing Javascript without an element ");
		} else {
			logStartAction(String.format("executing Javascript for element '%s'", element.getLocation()));
		}

		JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver().getWebDriver();
		if (Objects.isNull(element)){
			jse.executeScript(javascript);
		} else {
			jse.executeScript(javascript, element);
		}
		if (Objects.isNull(element)) {
			logStartAction("executed Javascript without an element");
		} else {
			logStartAction(String.format("executed Javascript for element '%s'", element.getLocation()));
		}

	}
	public String executeJavascripts(WebElement element, String javascript){
		String aaa = null;
		if (Objects.isNull(element)) {
			logStartAction("executing Javascript without an element ");
		} else {
			logStartAction(String.format("executing Javascript for element '%s'", element.getLocation()));
		}

		JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver().getWebDriver();
		if (Objects.isNull(element)){
			 aaa = (String)jse.executeScript(javascript);
		} else {
			jse.executeScript(javascript, element);
		}
		if (Objects.isNull(element)) {
			logStartAction("executed Javascript without an element");
		} else {
			logStartAction(String.format("executed Javascript for element '%s'", element.getLocation()));
		}
		return aaa;

	}

	public void executeJavascript(String javascript){
		executeJavascript(null, javascript);
	}
	public String executeJavascripts(String javascript){
		return executeJavascripts(null, javascript);
	}

	/**
	 * Get session storage of the browser
	 **/
	public SessionStorage getSessionStorage(){
		return new SessionStorage(webDriver);
	}

	/**
	 * Get local storage of the browser
	 * @return LocalStorage
	 */
	public LocalStorage getLocalStorage(){
		return new LocalStorage(webDriver);
	}

	/*****************
	 * Private methods
	 *****************/
	private void logStartAction(String msg) {
		System.out.println(String.format("		Executing: [%s]", msg));
	}

	private void logEndAction(String msg) {
		System.out.println(String.format("		Done: [%s]", msg));
	}

}


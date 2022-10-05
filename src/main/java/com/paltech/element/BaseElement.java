package com.paltech.element;
import com.paltech.constant.StopWatch;
import com.paltech.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BaseElement {
	// private final Locator locator;
	protected WebElement instance = null;
	protected final By locator;
	private final int timeOutInSeconds = 3;
	public final int timeOutShortInSeconds = 1;

	public By getLocator() {
		return locator;
	}

	public BaseElement(By locator) {
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

	protected WebElement getWebElement(int timeOutInSeconds) {
		synchronized (BaseElement.class) {
			instance = DriverManager.getDriver().findElement(locator, timeOutInSeconds);
		}
		return instance;
	}

	public List<WebElement> getWebElements() {
		return DriverManager.getDriver().findElements(locator);
	}

	protected void reload() {
		instance = DriverManager.getDriver().findElement(locator);
	}

	protected <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		return getWebElement().getScreenshotAs(target);
	}

	public void click() {
		try {
			logStartAction(String.format("click: '%s'", locator));
			getWebElement().click();
			logEndAction(String.format("clicked: '%s'", locator));
		/*} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));*/
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
	}

	public void jsClick(){
		logStartAction(String.format("click by javascript: '%s'", locator));
		try {
			DriverManager.getDriver().executeJavascript(getWebElement(), "arguments[0].click();");
			logEndAction(String.format("clicked by javascript: '%s'", locator));
		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}

	}
	protected void submit() {
		try{
			logStartAction(String.format("submit: '%s'", locator));
			getWebElement().submit();
			logEndAction(String.format("submitted: '%s'", locator));
		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
	}

	/**
	 * Sending keys such as End, Home
	 * @param key
	 */
	public void sendSingleKey(Keys key) {
		try{
			logStartAction("send key '%s' to: %s");
			Actions action = new Actions(DriverManager.getDriver().getWebDriver());
			action.sendKeys(key).build().perform();
			logEndAction("sent key '%s' to: %s");
		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
	}

	protected void sendKeys(CharSequence... keysToSend) {
		try{
			logStartAction(String.format("send keys '%s' to: %s", String.valueOf(keysToSend), locator));
			getWebElement().sendKeys(keysToSend);
			logEndAction(String.format("sent keys '%s' to: %s", String.valueOf(keysToSend), locator));
		/*} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));*/
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
	}

	protected void clear() {
		logStartAction(String.format("clear text: '%s'", locator));
		getWebElement().clear();
		logEndAction(String.format("cleared text: '%s'", locator));
	}

	protected void type(CharSequence... keysToSend) {
		logStartAction(String.format("type '%s' into: %s", String.valueOf(keysToSend), locator));
		clear();
		sendKeys(keysToSend);
		logEndAction(String.format("typed '%s' into: %s", String.valueOf(keysToSend), locator));
	}

	protected String getTagName() {
		try{
			logStartAction(String.format("get Tag Name: '%s'", locator));
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
			if(isLogged){logStartAction(String.format("get '%s' attribute: %s", name, locator));}
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
	 * Getting colour of this control
	 * @param name is 'color' if you want to get text color OR 'background-color' if you want to get background color
	 * @return colour
	 */
	public String getColour(String name) {
		try{
			logStartAction(String.format("get %s color: %s", name, locator));
			String value = getWebElement().getCssValue(name);
			logEndAction(String.format("got color '%s' is %s", value, locator));
			return value;
	/*	} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception at getColour is '%s'", ex.getMessage()));
			return "";*/
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
			return "";
		}
	}

	public String getColour() {
		return getColour("background-color");
	}

	/**
	 * Moving the cursor of the mouse to the location of this control
	 */
	public void moveAndHoverOnControl(){
		logStartAction(String.format("move and hover on this main menu: %s", locator));
		Actions builder = new Actions(DriverManager.getDriver().getWebDriver());
		builder.moveToElement(getWebElement()).build().perform();
		logEndAction(String.format("moved and hovered on this main menu: %s", locator));
	}

	/**
	 * Checking if this control is selected
	 * @return boolean
	 */
	public boolean isSelected() {
		try {
			logStartAction(String.format("check if this control '%s' is selected", locator));
			boolean isSelected = getWebElement().isSelected();
			logEndAction(String.format("checked if this control '%s' selected is  %s", locator, isSelected));
			return isSelected;

	/*	} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
			return false;*/
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
			logStartAction(String.format("get if this control '%s' is enabled status", locator));
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
	 * Checking if this control is displayed
	 * @return boolean
	 */
	public boolean isDisplayed() {
		return isDisplayed(timeOutShortInSeconds);
	}

	/**
	 * Checking if this control is displayed
	 * @param timeOutInSeconds how many seconds you want to wait
	 * @return boolean
	 */
	public boolean isDisplayedShort(int timeOutInSeconds) {
		try {
			logStartAction(String.format("check if this control '%s' is displayed after timeoutInSeconds %s", locator, timeOutInSeconds));
			boolean isDisplayed = DriverManager.getDriver().isElementVisible(locator, timeOutInSeconds);
			logEndAction(String.format("checked if this control displayed is %s with xpath '%s'", isDisplayed, locator));
			return isDisplayed;
		}  catch (StaleElementReferenceException ex) {
			logStartAction(String.format("Error: StaleElementReferenceException is still waiting for this element '%s'", locator));
			return false;
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
			return false;
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
			return false;
		}
	}

	public boolean isDisplayed(int timeOutInSeconds) {
		int count = 0;
		LOOP: while (true) {
			try{
				logStartAction(String.format("check if this control '%s' is displayed", locator));
				boolean isDisplayed = getWebElement(timeOutInSeconds).isDisplayed();
				logEndAction(String.format("checked if this control '%s' is displayed %s", locator, isDisplayed));
				return isDisplayed;
			} catch (NullPointerException ex) {
				logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
				return false;
			} catch (WebDriverException ex) {
				count++;
				System.out.println("Info: WebDriverException thrown");
				if(count == 3){
					logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
					return false;
				}
				continue LOOP;
			}
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
			logStartAction(String.format("check if this control '%s' is invisible after timeoutInSeconds %s", locator, timeoutInSeconds));
			boolean isInvisible = DriverManager.getDriver().isElementInvisible(locator, timeoutInSeconds);
			logEndAction(String.format("checked if this control '%s' is invisible %s", locator, isInvisible));
			return isInvisible;
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
			return false;
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
			return false;
		}
	}

	public void waitForControlInvisible(int timeOutDisplayed, int timeOutInvisible) {
		if(isDisplayed(timeOutShortInSeconds)){
		isInvisible(timeOutInvisible);
		}
	}

	public void waitForControlInvisible(){
		waitForControlInvisible(5, 5);
	}

	/**
	 * Checking if a control is clickable after period of time
	 * @param timeoutInSeconds how many seconds you want to wait for this control
	 * @return boolean
	 */
	public boolean isClickable(int timeoutInSeconds) {
		try {
			logStartAction(String.format("check if this control '%s' is clickable after timeoutInSeconds %s", locator, timeoutInSeconds));
			boolean isClickable = false;
			synchronized (BaseElement.class) {
				// must check again as one of the
				// blocked threads can still enter
				isClickable = DriverManager.getDriver().isElementClickable(locator, timeoutInSeconds);
			}
			logEndAction(String.format("checked if this control '%s' is clickable %s", locator, isClickable));
			return isClickable;
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
			return false;
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
			return false;
		}
	}

	/**
	 * Checking if a control is present after period of time
	 * @return boolean
	 */
	public boolean isPresent() {
		return isPresent(timeOutInSeconds);
	}

	public boolean isPresent(int timeOutInSeconds) {
		try {
			logStartAction(String.format("check if this control '%s' is present after timeoutInSeconds %s", locator, timeOutInSeconds));
			WebElement e = null;
			synchronized (BaseElement.class) {
				e = DriverManager.getDriver().findElementPresence(locator, timeOutInSeconds);
			}
			boolean isPresent = (e != null);
			logEndAction(String.format("checked if this control '%s' is present %s", locator, isPresent));
			return isPresent;
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
			return false;
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
			return false;
		} catch (Exception ex) {
			logEndAction(String.format("Exception: can not find element: '%s' after waiting for %s second(s)", locator, timeOutInSeconds));
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
		logStartAction(String.format("get text of this control '%s' after timeoutInSeconds %s", locator, timeOutInSeconds));
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
	 * Getting control's text with default 3 timeoutSecond
	 * @return
	 */
	public String getText() {
		return getText(3);
	}

	/**
	 * scrolling down a distance to be visible an invisible control
	 */
	public void scrollDownInDistance(){
		WebElement e = this.getWebElement();
		int x = e.getLocation().getX();
		int y = e.getLocation().getY() + 20;
		DriverManager.getDriver().executeJavascript(e, String.format("window.scrollTo(%s, %s);", x, y));
	}

	/**
	 * Scrolling to the top of this control or the bottom of this control
	 * @param isAlignedToTop if true, the top of the element will be aligned to the top of the visible area of the scrollable ancestor
	 *                       if false, the bottom of the element will be aligned to the bottom of the visible area of the scrollable ancestor.
	 *                       Default is true
	 */
	public void scrollToThisControl(boolean isAlignedToTop){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		while(stopWatch.getElapsedTime() < (2 * 1000)){
			try{
				WebElement e = this.getWebElement();
				if(e == null){
					throw new Exception(String.format("Error: Java-script Scroll to this control '%s'", locator));
				} else{
					DriverManager.getDriver().executeJavascript(e, String.format("arguments[0].scrollIntoView(%s);", isAlignedToTop));
					break;
				}
			} catch(NoSuchWindowException ex){
				System.out.println(String.format("Exception: Error while scrolling to Element using Java-script: '%s'", ex.getMessage()));
			} catch(WebDriverException ex ){
				System.out.println(String.format("Exception: Cannot interact with with Element '%s' because the window is closed.", this.locator));
			} catch (Exception ex){
				System.out.println(String.format("Exception:There was an exception when scrolling to Element '%s'. \nException: '%s'", this.locator, ex.getMessage()));
			}
		}

	}

	public void scrollToBottom(){
		try {
			logStartAction("Starting to scroll to the bottom of this control");
			DriverManager.getDriver().executeJavascript("window.scrollTo(0, document.body.scrollHeight);");
		} catch(WebDriverException ex ){
			System.out.println(String.format("WebDriverException: Cannot interact with with this control '%s' because the window is closed.", this.locator));
		} catch (Exception ex){
			System.out.println(String.format("Exception:There was an exception when scrolling to this control '%s'. \nException: '%s'", this.locator, ex.getMessage()));
		}

	}

	public void scrollToTop(){
		try {
			logStartAction("Starting to scroll to the top of this control");
			DriverManager.getDriver().executeJavascript("window.scrollTo(0, 0);");
		} catch(WebDriverException ex ){
			logEndAction(String.format("WebDriverException: Cannot interact with with Element '%s' because the window is closed.", this.locator));
		} catch (Exception ex){
			logEndAction(String.format("Exception:There was an exception when scrolling to Element '%s'. \nException: '%s'", this.locator, ex.getMessage()));
		}
	}

	public boolean isTextDisplayed(String expectedText, int timeOutInSeconds){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		while(stopWatch.getElapsedTime() <= timeOutInSeconds * 1000){
			try{
				String text = this.getText(1);
				if (expectedText.equals(text)){
					return true;
				}
			} catch (StaleElementReferenceException ex) {
				logStartAction("still waiting for text of this element displayed");
			} catch (TimeoutException ex) {
				logEndAction(String.format("Timeout: This element still displays after timeout %s", timeOutInSeconds));
				break;
			} catch (Exception ex) {
				logEndAction(String.format("Exception: can not find element after waiting for %s second(s)", timeOutInSeconds));
			}
		}
		return false;
	}

	/**
	 * Double click a this control
	 */
	public void doubleClick(){
		try {
			logStartAction("Double-click on this control");
			Actions action = new Actions(DriverManager.getDriver().getWebDriver());
			WebElement webElement = this.getWebElement();
			action.doubleClick(webElement).perform();
		} catch (TimeoutException ex) {
			logEndAction(String.format("Timeout: Cannot double-click on this control %s", timeOutInSeconds));
		} catch (Exception ex) {
			logEndAction(String.format("Exception: Cannot double-click on this control %s second(s)", timeOutInSeconds));
		}
	}

	/**
	 * Right click on this control
	 */
	public void rightClick(){
		try {
			logStartAction("Right-click on this control");
			Actions action = new Actions(DriverManager.getDriver().getWebDriver());
			WebElement webElement = this.getWebElement();
			action.contextClick(webElement).perform();
		} catch (TimeoutException ex) {
			logEndAction(String.format("Timeout: Cannot right-click on this control %s", timeOutInSeconds));
		} catch (Exception ex) {
			logEndAction(String.format("Exception: Cannot right-click on this control %s second(s)", timeOutInSeconds));
		}
	}

	/**
	 * Moving the mouse's cursor to this control
	 */
	public void moveToTheControl(){
		Actions action = new Actions(DriverManager.getDriver().getWebDriver());
		WebElement webElement = this.getWebElement();
		action.moveToElement(webElement).perform();
	}

	/******************
	 * Methods haven't validated yet
	 */
	protected Dimension getSize() {
		logStartAction(String.format("get Size: %s", locator));
		logEndAction(String.format("got Size '%s': %s", getWebElement().getSize(), locator));
		return getWebElement().getSize();
	}

	protected void mouseTo() {
		logStartAction(String.format("move to: %s", locator));
		Actions actions = new Actions(DriverManager.getDriver().getWebDriver());
		actions.moveToElement(getWebElement()).perform();
		logEndAction(String.format("moved to: %s", locator));
	}

	protected int count() {
		logStartAction(String.format("get items count : %s", locator));
		int count = getWebElements().size();
		logEndAction(String.format("got '%s' element(s): %s", count, locator));
		return getWebElements().size();
	}

	/**********************
	 * Private methods
	 *********************/

	protected void logStartAction(String msg) {
		System.out.println(String.format("[Element] Start action: %s", msg));
	}

	protected void logEndAction(String msg) {
		System.out.println(String.format("[Element] Done action: %s", msg));
	}

	public WebElement waitForElementToBePresent(By by) {
		return waitForElementToBePresent(by, timeOutInSeconds);
	}

	public WebElement waitForElementToBePresent(By by, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutInSeconds);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public void waitForAttributeChange(String attribute,String expected, int timeOutInSeconds)
	{
		String before = getAttribute(attribute);

		for (int i=0; i<timeOutInSeconds; i++)
		{
			if(before.contains(expected))
				break;
			before = getAttribute(attribute);

		}
	}
}

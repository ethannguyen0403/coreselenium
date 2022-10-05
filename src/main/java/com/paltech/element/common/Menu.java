package com.paltech.element.common;

import com.paltech.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class Menu {
	private final By locator;
	private WebElement instance = null;
	private By locatorItems;

	public By getLocator() {
		return locator;
	}

	public Menu(By locator) {
		this.locator = locator;
	}

	public Menu(By locator, By locatorChildren) {
		this.locator = locator;
		locatorItems = locatorChildren;
	}

	/**
	 * Checking if a control is invisible after period of time
	 * author: liam.ho
	 * @param timeoutInSeconds how many seconds you want to wait for this control
	 * @return boolean
	 */
	public boolean isInvisible(int timeoutInSeconds) {
		try {
			logStartAction(String.format("check if this control is invisible after timeoutInSeconds %s by locator '%s'", timeoutInSeconds, locator));
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

	public static Menu cssSelector(String selector) {
		return new Menu(By.cssSelector(selector));
	}

	public static Menu xpath(String xpathExpression) {
		return new Menu(By.xpath(xpathExpression));
	}

	public static Menu xpath(String xpathExpression, String xpathChildren) {
		return new Menu(By.xpath(xpathExpression), By.xpath(xpathChildren));
	}
	
	public static Menu id(String id) {
		return new Menu(By.id(id));
	}

	public static Menu name(String name) {
		return new Menu(By.name(name));
	}

	protected WebElement getWebElement() {
		// two or more threads might be here!!!
		synchronized (Menu.class) {
			// must check again as one of the
			// blocked threads can still enter
			return DriverManager.getDriver().findElement(locator);// safe
		}
	}

	/**
	 * Checking if this control is displayed
	 * @return boolean
	 */
	public boolean isDisplayed(int timeOutInSeconds) {
		try {
			logStartAction(String.format("check if this control '%s' is displayed after timeoutInSeconds %s", locator, timeOutInSeconds));
			boolean isDisplayed = DriverManager.getDriver().isElementVisible(locator, timeOutInSeconds);
			logEndAction(String.format("checked if this control displayed is %s with xpath '%s'", isDisplayed, locator));
			return isDisplayed;
		} catch (StaleElementReferenceException ex) {
			logEndAction("still waiting for text of this element displayed");
			return false;
		} catch (TimeoutException ex) {
			logEndAction("TimeoutException: This element still displays after timeout");
			return false;
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
			return false;
		}
	}

	public boolean isDisplayed() {
		return isDisplayed(3);
	}

	public void clickExpand() {
		try{
			logStartAction(String.format("click main menu: %s", locator));
			getWebElement().click();
			logEndAction(String.format("clicked main menu: %s", locator));
		} catch (TimeoutException ex) {
			logEndAction("TimeoutException: This element still displays after timeout");
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
		}
	}

	public void click() {
		clickExpand();
	}

	public void moveAndHoverOnControl(){
		logStartAction(String.format("move and hover on this main menu: %s", locator));
		Actions builder = new Actions(DriverManager.getDriver().getWebDriver());
		builder.moveToElement(getWebElement()).build().perform();
		logEndAction(String.format("moved and hovered on this main menu: %s", locator));
	}

	/**
	 * Getting text of main menu
	 * @return text
	 */
	public String getText() {
		try{
			logStartAction(String.format("get text: %s", locator));
			logEndAction(String.format("got text value '%s': %s", getWebElement().getText(), locator));
			return getWebElement().getText();
		} catch (TimeoutException ex) {
			logEndAction("TimeoutException: This element still displays after timeout");
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
		}
		return "";
	}

	/**
	 * Checking items within ddb is what items you are expected or not
	 * @param items item names that you want to check
	 * @return true or false
	 */
	public boolean areOptionsMatched(List<String> items, boolean isClicked){
		try {
			if (isClicked){
				this.instance.click();
			} else {
				this.moveAndHoverOnControl();
			}
		} catch (TimeoutException ex) {
			logEndAction("TimeoutException: This element still displays after timeout");
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
		}
		return areMatched(items);
	}

	/******************
	 * Private area
	 *****************/
	private void logStartAction(String msg) {
		System.out.println(String.format("[Element]Executing: %s", msg));
	}

	private void logEndAction(String msg) {
		System.out.println(String.format("[Element]Done: %s", msg));
	}

	private String selectItem(String name){
		List<WebElement> lstWebElements = DriverManager.getDriver().findElements(locatorItems);
		String text = "";
		for(WebElement e : lstWebElements){
			text = e.getText();
			if(text.toLowerCase().equals(name.toLowerCase())){
				e.click();
				return text;
			}
		}
		return text;
	}

	private boolean areMatched(List<String> items){
		List<WebElement> lstWebElements = DriverManager.getDriver().findElements(locatorItems);
		if (items.size() !=  lstWebElements.size()){
			System.out.println(String.format("A number of items within this ddb are %s != %s", items.size(),  lstWebElements.size()));
			return false;
		}
		for (int i=0; i < items.size(); i++){
			String itemWithinDropDown = lstWebElements.get(i).getText();
			if (!itemWithinDropDown.equals(items.get(i))){
				System.out.println(String.format("Error: Not match '%s' != '%s' at index %s", itemWithinDropDown, items.get(i), i + 1));
				return false;
			}
		}
		return true;
	}
}

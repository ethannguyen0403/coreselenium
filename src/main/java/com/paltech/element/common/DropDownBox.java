package com.paltech.element.common;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;
import com.paltech.driver.DriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class DropDownBox {
	private final By locator;
	Select instance = null;
	final int timeOutInSeconds = 5;

	public By getLocator() {
		return locator;
	}
	public DropDownBox(By locator) {
		this.locator = locator;
	}

	public static DropDownBox cssSelector(String selector) {
		return new DropDownBox(By.cssSelector(selector));
	}

	public static DropDownBox xpath(String xpathExpression) {
		return new DropDownBox(By.xpath(xpathExpression));
	}

	public static DropDownBox id(String id) {
		return new DropDownBox(By.id(id));
	}

	public static DropDownBox name(String name) {
		return new DropDownBox(By.name(name));
	}

	protected Select getWebElement() {
		synchronized (DropDownBox.class) {
			instance = new Select(DriverManager.getDriver().findElement(locator));
		}
		return instance;
	}

	public void selectByVisibleText(String text) {
		logStartAction(String.format("select an item '%s' with locator '%s'", text, locator));
		getWebElement().selectByVisibleText(text);
		logEndAction(String.format("selected the item '%s' with locator: '%s'", text, locator));
	}

	/**
	 * Select an item that contains expected text
	 * @param text
	 */
	public void selectByVisibleContainsText(String text){
		List<String> lst = new ArrayList<>();
		List<WebElement> lstWebElements = getWebElement().getOptions();
		if (lstWebElements.size()==0) return ;

		for (WebElement e : lstWebElements){
			if(e.getText().contains(text))
				selectByVisibleText(e.getText());
		}

	}

	public void selectByIndex(int index) {
		logStartAction(String.format("select an number item '%d' with locator '%s'", index, locator));
		getWebElement().selectByIndex(index);
		logEndAction(String.format("selected the number item '%d' with locator: '%s'", index, locator));
	}

	public String getFirstSelectedOption() {
		logStartAction(String.format("get text of the first selected option with locator '%s'", locator));
		try {
			String text = getWebElement().getFirstSelectedOption().getText();
			logEndAction(String.format("got text of the first selected option with locator '%s' is '%s'", locator, text));
			return getWebElement().getFirstSelectedOption().getText();
		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
			return "";
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
			return "";
		} catch (Exception ex) {
			logEndAction(String.format("Error: Exception exception is '%s'", ex.getMessage()));
			return "";
		}

	}


	public WebElement waitForElementToBePresent(By by) {
		return waitForElementToBePresent(by, timeOutInSeconds);
	}

	public WebElement waitForElementToBePresent(By by, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutInSeconds);
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public boolean areOptionsMatched(List<String> items){
		List<WebElement> lstWebElements = getWebElement().getOptions();
		if (items.size() !=  lstWebElements.size()){
			logEndAction(String.format("ERROR: The number of inputted items are %s != %s on the drop-down list", items.size(), lstWebElements.size()));
			return false;
		}
		for (int i=0; i < items.size(); i++){
			String itemWithinDropDown = lstWebElements.get(i).getText().trim();
			if (!itemWithinDropDown.equals(items.get(i))){
				logEndAction(String.format("ERROR: Not match '%s' on this drop-down list != '%s' of the inputted at index %s", itemWithinDropDown, items.get(i), i + 1));
				return false;
			}
		}
		return true;
	}

	public List<String> getOptions(){
		List<String> lst = new ArrayList<>();
		List<WebElement> lstWebElements = getWebElement().getOptions();
		if (lstWebElements.size()==0) return lst;

		for (WebElement e : lstWebElements){
			lst.add(e.getText());
		}
		return lst;
	}

	public int getNumberOfItems(){
		return this.getWebElement().getOptions().size();
	}

	public boolean isDisplayed(int timeOutInSeconds) {
		int count = 0;
		LOOP: while (true) {
			try{
				logStartAction(String.format("check if this control '%s' is displayed", locator));
				WebElement webElement= DriverManager.getDriver().findElement(locator, timeOutInSeconds);
				Boolean isDisplayed = webElement.isDisplayed();
				logEndAction(String.format("checked if this control '%s' is displayed %s", locator, isDisplayed));
				return isDisplayed;
			} catch (NullPointerException ex) {
				logEndAction(String.format("Error: NullPointerException is '%s'", ex.getMessage()));
				return false;
			} catch (WebDriverException ex) {
				count++;
				System.out.println("DEBUG: WebDriverException thrown");
				if(count == 3){
					logEndAction(String.format("ERROR: WebDriverException exception is '%s'", ex.getMessage()));
					return false;
				}
				continue LOOP;
			}
		}
	}

	public boolean isDisplayed() {
		return isDisplayed(timeOutInSeconds);
	}

	public boolean isEnabled() {
		try{
			logStartAction(String.format("check if this control '%s' is enabled", locator));
			WebElement webElement= DriverManager.getDriver().findElement(locator, timeOutInSeconds);
			Boolean isEnabled = webElement.isEnabled();
			logEndAction(String.format("checked if this control '%s' enabled is %s", locator, isEnabled));
			return isEnabled;
		} catch (NullPointerException ex) {
			logEndAction(String.format("Error: NullPointerException is '%s'", ex.getMessage()));
			return false;
		} catch (WebDriverException ex) {
			System.out.println("DEBUG: WebDriverException thrown");
			return false;
		}
	}


	/**********************
	 * Private methods
	 *********************/
	private void logStartAction(String msg) {
		System.out.println(String.format("[Element]Executing: %s", msg));
	}

	private void logEndAction(String msg) {
		System.out.println(String.format("[Element]Done: %s", msg));
	}
}

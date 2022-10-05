package com.paltech.element.common;

import com.paltech.driver.DriverManager;
import com.paltech.element.BaseElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class CheckBox extends BaseElement {

	private CheckBox(By locator) {
		super(locator);
	}

	public static CheckBox cssSelector(String selector) {
		return new CheckBox(By.cssSelector(selector));
	}

	public static CheckBox xpath(String xpathExpression) {
		return new CheckBox(By.xpath(xpathExpression));
	}
	
	public static CheckBox id(String id) {
		return new CheckBox(By.id(id));
	}

	public static CheckBox name(String name) {
		return new CheckBox(By.name(name));
	}

	public void select(){
		try {
			logStartAction(String.format("select this control '%s'", locator));
			if (!isSelected()){
				click();
			}
			logEndAction(String.format("selected this control '%s' successfully", locator));

		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
	}

	public void deSelect(){
		try {
			logStartAction(String.format("deselect this control '%s'", locator));
			if (isSelected()){
				click();
			}
			logEndAction(String.format("deselected this control '%s' successfully", locator));

		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
	}

	public void selectWithPresence(){
		try {
			logStartAction(String.format("select this control '%s'", locator));
			if (!isSelectedWithPresence(2)){
				jsClick(2);
			}
			logEndAction(String.format("selected this control '%s' successfully", locator));

		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		}
	}

	public void deSelectWithPresence(){
		try {
			logStartAction(String.format("deselect this control '%s'", locator));
			if (isSelectedWithPresence(2)){
				jsClick(2);
			}
			logEndAction(String.format("deselected this control '%s' successfully", locator));

		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
	}

	public boolean isSelectedWithPresence(int timeOutInSeconds){
		logStartAction(String.format("check whether this control is selected '%s'", locator));
		this.instance = findPresentElement(timeOutInSeconds);
		boolean flag = instance.isSelected();
		logEndAction(String.format("checked whether this control selected is %s", flag));
		return flag;
	}

	public WebElement findPresentElement(int timeOut){
		try {
			logStartAction(String.format("find this control '%s'", locator));
			synchronized (CheckBox.class) {
				this.instance = DriverManager.getDriver().findElementPresence(locator, timeOut);
			}
			logEndAction(String.format("found this control '%s' successfully", locator));
		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
		return instance;
	}

	public void jsClick(int timeOut){
		try {
			logStartAction(String.format("click this control '%s'", locator));
			this.instance = findPresentElement(timeOut);
			if (Objects.isNull(instance)){
				logEndAction(String.format("cannot click on this control '%s'", locator));
				return;
			}
			DriverManager.getDriver().executeJavascript(instance, "arguments[0].click();");
			logEndAction(String.format("clicked this control '%s' successfully", locator));
		} catch (NullPointerException ex) {
			logEndAction(String.format("Exception: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		}
	}

	public List<WebElement> getPresentElements() {
		return DriverManager.getDriver().findElementPresences(locator);
	}

}

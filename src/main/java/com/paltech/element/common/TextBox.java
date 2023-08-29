package com.paltech.element.common;

import org.openqa.selenium.By;
import com.paltech.element.BaseElement;
import org.openqa.selenium.WebDriverException;

public class TextBox extends BaseElement {

	public TextBox(By locator) {
		super(locator);
	}
	
	public static TextBox cssSelector(String selector) {
		return new TextBox(By.cssSelector(selector));
	}

	public static TextBox xpath(String xpathExpression) {
		return new TextBox(By.xpath(xpathExpression));
	}
	
	public static TextBox id(String id) {
		return new TextBox(By.id(id));
	}

	public static TextBox name(String name) {
		return new TextBox(By.name(name));
	}

	public void sendKeys(CharSequence... keysToSend) {
		try {
			this.clear();
			super.sendKeys(keysToSend);
//		} catch (NullPointerException ex) {
//			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		} catch (Exception ex) {
			logEndAction(String.format("Exception: can not find element: '%s'", ex.getMessage()));
		}

	}
	public void sendKeys1(CharSequence... keysToSend) {
		try {
			this.clear();
			super.sendKeys(keysToSend);
//		} catch (NullPointerException ex) {
//			logEndAction(String.format("Error: NullPointer exception is '%s'", ex.getMessage()));
		} catch (WebDriverException ex) {
			logEndAction(String.format("Error: WebDriverException exception is '%s'", ex.getMessage()));
		} catch (Exception ex) {
			logEndAction(String.format("Exception: can not find element: '%s'", ex.getMessage()));
		}

	}

	public void type(CharSequence... keysToSend) {
		this.clear();
		super.sendKeys(keysToSend);
	}
	public void type(boolean isClear, CharSequence... keysToSend) {
		if(isClear)
			this.clear();
		super.sendKeys(keysToSend);
	}
	public String getText(int timeOutSeconds) {
		return super.getText(timeOutSeconds);
	}

	public String getAttribute(){
		return super.getAttribute("value");
	}
}

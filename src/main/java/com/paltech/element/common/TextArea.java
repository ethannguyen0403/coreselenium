package com.paltech.element.common;

import com.paltech.driver.DriverManager;
import com.paltech.element.BaseElement;
import org.openqa.selenium.By;

public class TextArea extends BaseElement {

	public TextArea(By locator) {
		super(locator);
	}
	
	public static TextArea xpath(String xpathExpression) {
		return new TextArea(By.xpath(xpathExpression));
	}
	
	public void sendInnerText(String text) {
		DriverManager.getDriver().executeJavascript(this.getWebElement(), String.format("arguments[0].innerHTML = '<p>%s</p>'", text));
	}

	public String getText(int timeOutSeconds) {
		return super.getText(timeOutSeconds);
	}
}

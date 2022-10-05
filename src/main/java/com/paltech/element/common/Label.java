package com.paltech.element.common;

import org.openqa.selenium.By;
import com.paltech.element.BaseElement;

public class Label extends BaseElement {

	private Label(By locator) {
		super(locator);
	}
	
	public static Label cssSelector(String selector) {
		return new Label(By.cssSelector(selector));
	}

	public static Label xpath(String xpathExpression) {
		return new Label(By.xpath(xpathExpression));
	}
	
	public static Label id(String id) {
		return new Label(By.id(id));
	}

	public static Label name(String name) {
		return new Label(By.name(name));
	}

	public String getText(int timeOutSeconds) {
		return super.getText(timeOutSeconds);
	}

	public String getText() {
		return super.getText(timeOutShortInSeconds);
	}

}

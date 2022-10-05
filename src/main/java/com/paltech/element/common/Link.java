package com.paltech.element.common;

import org.openqa.selenium.By;
import com.paltech.element.BaseElement;

public class Link extends BaseElement {

	public Link(By locator) {
		super(locator);
	}

	public static Link cssSelector(String selector) {
		return new Link(By.cssSelector(selector));
	}

	public static Link xpath(String xpathExpression) {
		return new Link(By.xpath(xpathExpression));
	}

	public static Link id(String id) {
		return new Link(By.id(id));
	}

	public static Link name(String name) {
		return new Link(By.name(name));
	}

	public String getText(int timeOutSeconds) {
		return super.getText(timeOutSeconds);
	}
}

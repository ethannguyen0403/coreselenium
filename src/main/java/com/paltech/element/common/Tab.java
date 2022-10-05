package com.paltech.element.common;

import com.paltech.element.BaseElement;
import org.openqa.selenium.By;

public class Tab extends BaseElement {

	private Tab(By locator) {
		super(locator);
	}

	public static Tab cssSelector(String selector) {
		return new Tab(By.cssSelector(selector));
	}

	public static Tab xpath(String xpathExpression) {
		return new Tab(By.xpath(xpathExpression));
	}
	
	public static Tab id(String id) {
		return new Tab(By.id(id));
	}

	public static Tab name(String name) {
		return new Tab(By.name(name));
	}

}

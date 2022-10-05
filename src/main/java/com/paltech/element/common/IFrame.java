package com.paltech.element.common;

import com.paltech.element.BaseElement;
import org.openqa.selenium.By;

public class IFrame extends BaseElement {

	private IFrame(By locator) {
		super(locator);
	}
	
	public static IFrame cssSelector(String selector) {
		return new IFrame(By.cssSelector(selector));
	}

	public static IFrame xpath(String xpathExpression) {
		return new IFrame(By.xpath(xpathExpression));
	}
	
	public static IFrame id(String id) {
		return new IFrame(By.id(id));
	}

	public static IFrame name(String name) {
		return new IFrame(By.name(name));
	}

}

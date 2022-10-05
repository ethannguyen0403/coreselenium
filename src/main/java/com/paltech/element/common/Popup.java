package com.paltech.element.common;

import com.paltech.element.BaseElement;
import org.openqa.selenium.By;

public class Popup extends BaseElement {

	private Popup(By locator) {
		super(locator);
	}

	public static Popup cssSelector(String selector) {
		return new Popup(By.cssSelector(selector));
	}

	public static Popup xpath(String xpathExpression) {
		return new Popup(By.xpath(xpathExpression));
	}
	
	public static Popup id(String id) {
		return new Popup(By.id(id));
	}

	public static Popup name(String name) {
		return new Popup(By.name(name));
	}

}

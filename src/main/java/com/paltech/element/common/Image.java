package com.paltech.element.common;

import com.paltech.element.BaseElement;
import org.openqa.selenium.By;

public class Image extends BaseElement {

	private Image(By locator) {
		super(locator);
	}
	
	public static Image cssSelector(String selector) {
		return new Image(By.cssSelector(selector));
	}

	public static Image xpath(String xpathExpression) {
		return new Image(By.xpath(xpathExpression));
	}
	
	public static Image id(String id) {
		return new Image(By.id(id));
	}

	public static Image name(String name) {
		return new Image(By.name(name));
	}

}

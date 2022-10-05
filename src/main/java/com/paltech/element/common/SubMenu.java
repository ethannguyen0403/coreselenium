package com.paltech.element.common;

import com.paltech.element.SimpleElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SubMenu extends SimpleElement {

	public SubMenu(By locator) {
		super(locator);
	}

	public static SubMenu cssSelector(String selector) {
		return new SubMenu(By.cssSelector(selector));
	}

	public static SubMenu xpath(String xpathExpression) {
		return new SubMenu(By.xpath(xpathExpression));
	}
	
	public static SubMenu id(String id) {
		return new SubMenu(By.id(id));
	}

	public static SubMenu name(String name) {
		return new SubMenu(By.name(name));
	}

	public WebElement getWebElement() {
		return getWebElement(true);
	}
}

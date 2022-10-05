package com.paltech.element.common;

import com.paltech.driver.DriverManager;
import org.openqa.selenium.By;
import com.paltech.element.BaseElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class Button extends BaseElement {

	private Button(By locator) {
		super(locator);
	}

	public static Button cssSelector(String selector) {
		return new Button(By.cssSelector(selector));
	}

	public static Button xpath(String xpathExpression) {
		return new Button(By.xpath(xpathExpression));
	}
	
	public static Button id(String id) {
		return new Button(By.id(id));
	}

	public static Button name(String name) {
		return new Button(By.name(name));
	}
}

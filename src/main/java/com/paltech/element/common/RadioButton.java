package com.paltech.element.common;

import com.paltech.element.BaseElement;
import org.openqa.selenium.By;

public class RadioButton extends BaseElement {

	private RadioButton(By locator) {
		super(locator);
	}
	
	public static RadioButton cssSelector(String selector) {
		return new RadioButton(By.cssSelector(selector));
	}

	public static RadioButton xpath(String xpathExpression) {
		return new RadioButton(By.xpath(xpathExpression));
	}
	
	public static RadioButton id(String id) {
		return new RadioButton(By.id(id));
	}

	public static RadioButton name(String name) {
		return new RadioButton(By.name(name));
	}

}

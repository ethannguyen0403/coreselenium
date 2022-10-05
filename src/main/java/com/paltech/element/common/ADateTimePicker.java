package com.paltech.element.common;

import org.openqa.selenium.By;

import com.paltech.element.BaseElement;

public abstract class ADateTimePicker extends BaseElement {

	public ADateTimePicker(By locator) {
		super(locator);
	}

	public abstract void selectDateTime(String datetime);
}

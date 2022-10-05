package com.paltech.element.common;

import com.paltech.constant.StopWatch;
import com.paltech.element.BaseElement;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

public class Icon extends BaseElement {

	private Icon(By locator) {
		super(locator);
	}
	
	public static Icon cssSelector(String selector) {
		return new Icon(By.cssSelector(selector));
	}

	public static Icon xpath(String xpathExpression) {
		return new Icon(By.xpath(xpathExpression));
	}
	
	public static Icon id(String id) {
		return new Icon(By.id(id));
	}

	public static Icon name(String name) {
		return new Icon(By.name(name));
	}

	public boolean isTextChanged(String currentText, int timeOutInSeconds){
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		while(stopWatch.getElapsedTime() <= (long)(timeOutInSeconds * 1000)) {
			try {
				String ex = this.getText(1);
				if(!currentText.equals(ex)) {
					return true;
				}
			} catch (StaleElementReferenceException var5) {
				this.logStartAction("still waiting for text of this element changed");
			} catch (TimeoutException var6) {
				this.logEndAction(String.format("Timeout: Text of this control is not changed after timeout %s", new Object[]{Integer.valueOf(timeOutInSeconds)}));
				break;
			} catch (Exception var7) {
				System.out.println(String.format("Exception: can not find element after waiting for %s second(s)", new Object[]{Integer.valueOf(timeOutInSeconds)}));
			}
		}
		return false;
	}

}

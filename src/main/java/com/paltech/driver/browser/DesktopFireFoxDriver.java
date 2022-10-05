package com.paltech.driver.browser;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.paltech.driver.Driver;
import com.paltech.driver.DriverProperties;

public class DesktopFireFoxDriver extends Driver {

	public DesktopFireFoxDriver(DriverProperties properties) throws MalformedURLException {
		super(properties);
		if (properties.getRemoteURL() == null || properties.getRemoteURL().equals("")) {
//			System.out.println(String.format("Picking up FireFox executable at " + properties.getExecutablePath()));
			setWebDriver(new FirefoxDriver());
			System.out.println(String.format("PASS THIS STEP " ));
		} else {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setVersion(properties.getBrowserVersion());
			capabilities.setCapability(CapabilityType.PLATFORM, properties.getPlatform());
			setWebDriver(new RemoteWebDriver(new URL(properties.getRemoteURL()), capabilities));
		}
	}

}

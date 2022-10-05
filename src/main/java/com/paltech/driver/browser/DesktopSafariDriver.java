package com.paltech.driver.browser;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.paltech.driver.Driver;
import com.paltech.driver.DriverProperties;

public class DesktopSafariDriver extends Driver {

	public DesktopSafariDriver(DriverProperties properties) throws MalformedURLException {
		super(properties);

		if (properties.getRemoteURL() == null || properties.getRemoteURL().equals("")) {
			System.out.println(String.format("Picking up Safari executable at " + properties.getExecutablePath()));
			setWebDriver(new SafariDriver());
		} else {
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			capabilities.setVersion(properties.getBrowserVersion());
			capabilities.setCapability(CapabilityType.PLATFORM, properties.getPlatform());
			setWebDriver(new RemoteWebDriver(new URL(properties.getRemoteURL()), capabilities));
		}
	}

}

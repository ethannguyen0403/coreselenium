package com.paltech.driver.browser;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.paltech.driver.Driver;
import com.paltech.driver.DriverProperties;

public class DesktopInternetExplorerDriver extends Driver {

	public DesktopInternetExplorerDriver(DriverProperties properties) throws MalformedURLException {
		super(properties);
		// TODO Auto-generated constructor stub
		if (properties.getRemoteURL() == null || properties.getRemoteURL().equals("")) {
			System.out.println(
					String.format("Picking up Internet Explorer executable at " + properties.getExecutablePath()));
			System.setProperty("webdriver.ie.driver", properties.getExecutablePath());
			setWebDriver(new InternetExplorerDriver());
		} else {
			InternetExplorerOptions browserOptions = new InternetExplorerOptions();
			browserOptions.setCapability("version",properties.getBrowserVersion());
			browserOptions.setCapability("platform",properties.getPlatform());
//			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//			capabilities.setVersion(properties.getBrowserVersion());
//			capabilities.setCapability(CapabilityType.PLATFORM, properties.getPlatform());
			setWebDriver(new RemoteWebDriver(new URL(properties.getRemoteURL()), browserOptions));
		}
	}

}

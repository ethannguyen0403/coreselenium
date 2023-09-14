package com.paltech.driver.cloud;

import java.net.MalformedURLException;
import java.net.URL;

import com.saucelabs.common.SauceOnDemandAuthentication;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.paltech.constant.CoreConstants.Browser;
import com.paltech.constant.CoreConstants.Platform;
import com.paltech.driver.Driver;
import com.paltech.driver.DriverProperties;

public class SauceLabsDriver extends Driver {
	public SauceLabsDriver(DriverProperties properties) throws MalformedURLException {
		super(properties);
//		String username = "Partner_Test";
//		String accesskey = "7254afad-1964-44c7-b554-4db0ad1cf1cc";
		String username = System.getenv("SAUCE_USERNAME");
		String accesskey = System.getenv("SAUCE_ACCESS_KEY");

		System.out.println("username ==" + username);
		System.out.println("accesskey ==" + accesskey);

		SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);

		DesiredCapabilities capabilities = new DesiredCapabilities();

		// set desired capabilities to launch appropriate browser on Sauce
		if (properties.getBrowserName() == Browser.EDGE) {
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "MicrosoftEdge");
		}
		else if (properties.getBrowserName() == Browser.INTERNET_EXPLORER) {
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
		}
		else {
			capabilities.setCapability(CapabilityType.BROWSER_NAME, properties.getBrowserName());
		}

		if (properties.getPlatform() == Platform.OSX) {
			capabilities.setCapability(CapabilityType.PLATFORM_NAME, "OS X " + properties.getPlatformVersion());
		}
		else {
			capabilities.setCapability(CapabilityType.PLATFORM_NAME, properties.getPlatform() + " " + properties.getPlatformVersion());
		}

		capabilities.setCapability(CapabilityType.BROWSER_VERSION, properties.getBrowserVersion());
		System.out.println("Score method name ===="+ properties.getMethodName());
		if (properties.getMethodName() != null && !properties.getMethodName().equals("")){

			capabilities.setCapability("name", properties.getMethodName());
		}

		// Launch remote browser and set it as the current thread
		setWebDriver(new RemoteWebDriver(new URL("https://" + authentication.getUsername() + ":"
				+ authentication.getAccessKey() + "@ondemand.saucelabs.com:443/wd/hub"), capabilities));
	}
}

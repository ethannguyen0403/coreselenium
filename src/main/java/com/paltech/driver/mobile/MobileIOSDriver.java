package com.paltech.driver.mobile;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import com.paltech.driver.Driver;
import com.paltech.driver.DriverProperties;
import io.appium.java_client.ios.IOSDriver;
//import io.appium.java_client.remote.MobileCapabilityType;
/**
 * @author Liam.Ho
 * created on Nov/9/2019.
 */
public class MobileIOSDriver extends Driver {

	public MobileIOSDriver(DriverProperties properties) throws MalformedURLException {
		super(properties);
		DesiredCapabilities capabilities = new DesiredCapabilities();
//		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "MAC");
//		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, properties.getPlatformVersion());
//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getDeviceName());
//		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
		capabilities.setCapability("safariInitialUrl", "https://www.apple.com/");
		setWebDriver(new IOSDriver(new URL(properties.getRemoteURL()), capabilities));
	}

}

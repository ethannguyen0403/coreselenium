package com.paltech.driver.mobile;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import com.paltech.driver.Driver;
import com.paltech.driver.DriverProperties;
/**
 * @author Liam.Ho
 * created on Nov/9/2019.
 */
public class MobileAndroidDriver extends Driver {

	public MobileAndroidDriver(DriverProperties properties) throws MalformedURLException {
		super(properties);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, WordUtils.capitalize(properties.getPlatform().toString().toLowerCase()));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, properties.getPlatformVersion());
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getDeviceName());
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, WordUtils.capitalize(properties.getBrowserName().toString().toLowerCase()));
		setWebDriver(new AndroidDriver(new URL(properties.getRemoteURL()), capabilities));
	}

}

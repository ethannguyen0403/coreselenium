package com.paltech.driver.mobile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.MobileCapabilityType;
import com.paltech.driver.Driver;
import com.paltech.driver.DriverProperties;
import static com.paltech.driver.mobile.AppiumServer.startAppiumServer;

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
		capabilities.setCapability(MobileCapabilityType.UDID, properties.getUDID());
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, properties.getAutomationName());
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		startAppiumServer(capabilities);
		setWebDriver(new AppiumDriver(new URL(properties.getRemoteURL()), capabilities));
		getWebDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		setWebDriver(new AndroidDriver(new URL(properties.getRemoteURL()), capabilities));
	}

}

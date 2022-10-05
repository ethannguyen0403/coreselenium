package com.paltech.driver;

import java.io.File;
import com.paltech.constant.CoreConstants.Browser;
import com.paltech.constant.CoreConstants.Platform;
import net.lightbody.bmp.BrowserMobProxy;

public class DriverProperties {

	private Browser browserName;
	private String browserVersion;
	private Platform platform;
	private String platformVersion;
	private String remoteURL;
	private String executablePath;
	private boolean isProxy = false;
	private BrowserMobProxy browserMobProxy;
	private int elementWaitTimeOut = 1;
	// only for mobile
	private String deviceName;
	// only for SauceLabs cloud
	private String methodName;

	public Browser getBrowserName() {
		return browserName;
	}

	public void setBrowserName(Browser browserName) {
		this.browserName = browserName;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public String getRemoteURL() {
		return remoteURL;
	}

	public void setRemoteURL(String remoteURL) {
		this.remoteURL = remoteURL;
	}

	public String getExecutablePath() {
		return executablePath;
	}

	public void setExecutablePath(String pathToExecutable) {
		File file = new File("");
		this.executablePath = file.getAbsolutePath() + pathToExecutable;
	}

	public int getElementWaitTimeOut() {
		return elementWaitTimeOut;
	}

	public void setElementWaitTimeOut(int elementWaitTimeOut) {
		this.elementWaitTimeOut = elementWaitTimeOut;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String device) {
		this.deviceName = device;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public void setIsProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

	public BrowserMobProxy getBrowserMobProxy() {
		return browserMobProxy;
	}

	public void setBrowserMobProxy(BrowserMobProxy browserMobProxy) {
		this.browserMobProxy = browserMobProxy;
	}

}

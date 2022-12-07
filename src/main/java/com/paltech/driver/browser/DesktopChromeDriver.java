package com.paltech.driver.browser;

import com.paltech.driver.Driver;
import com.paltech.driver.DriverProperties;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
/**
 * @author isabella.huynh
 * created on Nov/9/2019.
 */
public class DesktopChromeDriver extends Driver {
	public DesktopChromeDriver(DriverProperties properties) throws MalformedURLException {
		super(properties);
		System.out.println(String.format("Picking up ChromeDriver at %s", properties.getExecutablePath()));

		ChromeOptions options = new ChromeOptions();
		options = configureChromeOptions();

		if (properties.isProxy()){
			System.out.println(String.format("Setting proxy is %s", properties.isProxy()));
			System.setProperty("webdriver.chrome.driver", properties.getExecutablePath());

			// creating a mob proxy
			BrowserMobProxy proxy = getProxyServer();

			// creating a selenium proxy
			Proxy seleniumProxy = getSeleniumProxy(proxy);
			options.setCapability(CapabilityType.PROXY, seleniumProxy);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
			setWebDriver(new ChromeDriver(options));
			proxy.newHar();
			properties.setBrowserMobProxy(proxy);
			System.out.println("DEBUG: Proxy Port is " + proxy.getPort());
		} else if (properties.getRemoteURL() == null || properties.getRemoteURL().equals("")) {
			System.setProperty("webdriver.chrome.driver", properties.getExecutablePath());
			setWebDriver(new ChromeDriver(options));
		} else {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setVersion(properties.getBrowserVersion());
			capabilities.setCapability("platform", properties.getPlatform());
			setWebDriver(new RemoteWebDriver(new URL(properties.getRemoteURL()), capabilities));
		}
	}


	/********************
	 * Private methods
	 *********************/

	private BrowserMobProxy getProxyServer() {
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		try {
			proxy.setTrustAllServers(true);
			// above line is needed for application with invalid certificates
			proxy.start();
		} catch (RuntimeException ex){
			System.err.println("EXCEPTION: RuntimeException occurs at getProxyServer");
		}
		return proxy;
	}

	private Proxy getSeleniumProxy(BrowserMobProxy proxyServer) {
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxyServer);
		try {
			String hostIp = Inet4Address.getLocalHost().getHostAddress();
			seleniumProxy.setHttpProxy(hostIp + ":" + proxyServer.getPort());
			seleniumProxy.setSslProxy(hostIp + ":" + proxyServer.getPort());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (RuntimeException ex){
			System.err.println("Exception: RuntimeException occurs at getSeleniumProxy");
		}
		return seleniumProxy;
	}

	private ChromeOptions configureChromeOptions(){

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
//		options.setBinary("C:\\Users\\isabella.huynh\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
		options.addArguments("--window-size=1920,1080");
		return options;
	}
}
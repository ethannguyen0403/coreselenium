package com.paltech.driver.mobile;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class AppiumServer {
    private static AppiumServiceBuilder builder;
    private static AppiumDriverLocalService service;

    public static void startAppiumServer(DesiredCapabilities cap) {
        builder = new AppiumServiceBuilder().withArgument(() -> "--base-path", "/wd/hub");
        builder.withArgument(() -> "--allow-insecure","chromedriver_autodownload");
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(4723);
        builder.withCapabilities(cap);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE, "true");
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
        builder.withLogFile(new File(System.getProperty("user.dir")+"appium.log"));
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        service.stop();
        cap.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
        builder.withCapabilities(cap);
        service.start();
    }


}

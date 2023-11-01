package com.paltech.driver.mobile;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;

public class AppiumServer {
    private static AppiumServiceBuilder builder;
    private static AppiumDriverLocalService service;

    public static void startAppiumServer() {
        builder = new AppiumServiceBuilder().withArgument(() -> "--base-path", "/wd/hub");
        builder.withArgument(() -> "--allow-insecure","chromedriver_autodownload");
        builder.withIPAddress("127.0.0.1");
        builder.withLogFile(new File(System.getProperty("user.dir")+"appium.log"));
        builder.usingPort(4723);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }


}

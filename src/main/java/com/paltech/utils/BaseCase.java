package com.paltech.utils;

import org.testng.Assert;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCase {
    public static void log(ExtentTest logger, String message) {
        logger.log(LogStatus.INFO, message);//For extentTest HTML report
        System.out.println(message);
        Reporter.log(message);
    }

    public static void logBug(ExtentTest logger, String message) {
        logger.log(LogStatus.ERROR, message);
        System.out.println(message);
        Reporter.log(message);
    }

    public boolean isTableEmpty(List<ArrayList<String>> lstRecord, String errorMessage, boolean isThrown){
        if (lstRecord.size() < 2){
            Assert.fail(String.format("Error: lstRecord.size() < %s ", lstRecord.size()));
        }
        if (lstRecord.size() == 2 && lstRecord.get(1).get(0).equals("No Record Found")){
            if (isThrown){
                Assert.fail(String.format("Error: There is no record found %s", errorMessage));
            } else {
                return true;
            }
        }
        return false;
    }


}

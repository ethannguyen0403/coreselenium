package com.paltech.utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExtentReporterNG implements IReporter {
    private ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String suiteName = getSuiteName(suites);
        extent = new ExtentReports(outputDirectory + File.separator + suiteName, true);
        try {
            for (ISuite suite : suites) {
                Map<String, ISuiteResult> result = suite.getResults();
                for (ISuiteResult r : result.values()) {
                    ITestContext context = r.getTestContext();
                    if (suiteName.isEmpty()){
                        suiteName = context.getCurrentXmlTest().getSuite().getName() + ".html";
                    }
                    buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                    buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                    buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("Exception: An exception at generateReport");
        } finally {
            extent.flush();
            extent.close();
        }
    }

    private String getSuiteName(List<ISuite> suites){
        String suiteName = "";
        try {
            for (ISuite suite : suites) {
                Map<String, ISuiteResult> result = suite.getResults();
                for (ISuiteResult r : result.values()) {
                    ITestContext context = r.getTestContext();
                    if (suiteName.isEmpty()){
                        suiteName = context.getCurrentXmlTest().getSuite().getName() + ".html";
                        return suiteName;
                    }
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("Exception: An exception at generateReport");
        }
        return "ExtentReportsTestNG.html";
    }

    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;
        try {
            if (tests.size() > 0) {
                for (ITestResult result : tests.getAllResults()) {
                    test = extent.startTest(result.getMethod().getMethodName());
                    test.getTest().setStartedTime(getTime(result.getStartMillis()));
                    test.getTest().setEndedTime(getTime(result.getEndMillis()));

                    // Adding INFO, DEBUG, FAIL logging message
                    for(String message: Reporter.getOutput(result)){
                        test.log(LogStatus.INFO, message);
                    }
                    for (String group : result.getMethod().getGroups())
                        test.assignCategory(group);
                    String message = "Test " + status.toString().toLowerCase() + "ed";

                    // Refer: https://github.com/anshooarora/extentreports-java/issues/102
                    if (result.getThrowable() != null)
                        message = result.getThrowable().getMessage();

                    if(!status.equals(LogStatus.PASS)){
                        String srcBase64 = result.getAttribute(result.getMethod().getMethodName()).toString();
                        test.log(status, message + "<div class=\"material-placeholder\"><img style=\"width:400px;height:200px;\" id=\"input12\" class=\"report-img materialboxed initialized\" " +
                                "src=\"data:image/png;base64, " + srcBase64 + "\" onMouseOver=\"this.style.transform='scale(2)', this.style.opacity=1\" onMouseOut=\"this.style.transform='scale(1)'\" align=\"left\"></div>");
                    } else {
                        test.log(status, message);
                    }
                    extent.endTest(test);
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("Exception: An exception at buildTestNodes of TestNG");
        }
    }
 
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();        
    }
}
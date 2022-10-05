package com.paltech.utils;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class GeneralListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult result) {
        /**
         *  Invoked each time before a test will be invoked.
         */
        super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        /**
         * Invoked each time a test succeeds.
         */
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        /**
         * Invoked each time a test fails.
         */
        super.onTestFailure(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        /**
         * Invoked each time a test is skipped.
         */
        super.onTestSkipped(tr);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
        /**
         * Invoked each time a method fails but has been annotated with
         * successPercentage and this failure still keeps it within the success percentage requested.
         */
        super.onTestFailedButWithinSuccessPercentage(tr);
    }

    @Override
    public void onStart(ITestContext testContext) {
        /**
         * Invoked after the test class is instantiated and before any configuration method is called.
         */
        super.onStart(testContext);
    }

    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        /**
         * Invoked whenever a configuration method succeeded.
         */
        super.onConfigurationSuccess(itr);
    }

    @Override
    public void onConfigurationFailure(ITestResult tr) {
        /**
         * Invoked whenever a configuration method failed.
         */
        super.onConfigurationFailure(tr);
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
        /**
         * Invoked whenever a configuration method was skipped.
         */
        super.onConfigurationSkip(itr);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        /**
         * Invoked after all the tests have run and all their Configuration methods have been called.
         */
        super.onFinish(testContext);
    }

}

package com.paltech.utils;

import org.testng.annotations.*;

public class GeneralTestBase {

    @BeforeSuite
    public void beforeSuite() {
        // TODO Auto-generated method stub
    }

    @AfterSuite
    public void afterSuite() {
        // TODO Auto-generated method stub
        System.out.println("A-afterSuite");
    }

    @BeforeTest
    public void beforeTest() {
        // TODO Auto-generated method stub
        System.out.println("A-beforeTest");
    }

    @AfterTest
    public void afterTest() {
        // TODO Auto-generated method stub
        System.out.println("A-afterTest");
    }

    @BeforeGroups
    public void beforeGroups() {
        // TODO Auto-generated method stub
        System.out.println("A-beforeGroups");
    }

    @AfterGroups
    public void afterGroups() {
        // TODO Auto-generated method stub
        System.out.println("A-afterGroups");
    }

    @BeforeClass
    public void beforeClass() {
        // TODO Auto-generated method stub
        System.out.println("A-beforeClass");
    }

    @AfterClass
    public void afterClass() {
        // TODO Auto-generated method stub
        System.out.println("A-afterClass");
    }

    @BeforeMethod
    public void beforeMethod() {
        // TODO Auto-generated method stub
        System.out.println("A-beforeMethod");
    }

    @AfterMethod
    public void afterMethod() {
        // TODO Auto-generated method stub
        System.out.println("A-afterMethod");
    }

}

package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver()); // Pass the WebDriver instance here
        homePage = new HomePage(getDriver()); // Pass the WebDriver instance here
    }

    @Test
    public void verifyOrangeHRMLogo(){
        loginPage.login(
                getProp().getProperty("username"),
                getProp().getProperty("password")
        );
        Assert.assertTrue(homePage.verifyOrangeHRMLogo(), "Logo is not visible on the Home Page");
    }

}

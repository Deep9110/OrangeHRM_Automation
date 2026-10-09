package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver()); // Pass the WebDriver instance here
        homePage = new HomePage(getDriver()); // Pass the WebDriver instance here
    }

    @Test
    public void verifyValidLoginTest() {
//        loginPage.login("Admin", "admin123");
//        Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after Successfull login.");

        loginPage.login(
                getProp().getProperty("username"),
                getProp().getProperty("password")
        );

        Assert.assertTrue(
                homePage.isAdminTabVisible(),
                "Admin tab should be visible after successful login."
        );
        homePage.logout();
        staticWait(2);
    }

    @Test
    public void invalidLoginTest(){
        loginPage.login("admin", "admin");
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.VerifyErrorMessage(expectedErrorMessage), "Test Failed: Invalid Error Message");
    }
}

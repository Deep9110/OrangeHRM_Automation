package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private ActionDriver actionDriver;

    //Define Locator using By class
    private By adminTab = By.xpath("//span[text()='Admin']");
    private By userIdButton = By.className("oxd-userdropdown-name");
    private By logoutButton = By.xpath("//a[text()='Logout']");
    private By OrangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

    //initialize ActionDriver object by passing Web driver instance
//    public HomePage(WebDriver driver){
//       this.actionDriver = new ActionDriver(driver);
//    }

    public HomePage(WebDriver driver){
        this.actionDriver = BaseClass.getActionDriver();
    }

    //Method to Verify if Admin tab is visible or not
    public boolean isAdminTabVisible() {
        return actionDriver.isDisplayed(adminTab);
    }

    //Method to Verify if Admin tab is visible or not
    public boolean verifyOrangeHRMLogo() {
        return actionDriver.isDisplayed(OrangeHRMLogo);
    }

    //method to perform Logout operation
    public void logout() {
        actionDriver.click(userIdButton);
        actionDriver.click(logoutButton);
    }
}

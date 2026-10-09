package com.orangehrm.actiondriver;

import com.orangehrm.base.BaseClass;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;
    public static final Logger logger = BaseClass.logger;

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        logger.info("WebDriver instance is created.");
    }

    //Method to click an element
    public void click(By by) {
        String elementDescription = getElementDescription(by);
        try {
            waitForElementToBeClickable(by);
            driver.findElement(by).click();
            logger.info("clicked an Element -->" + elementDescription);
        } catch (Exception e) {
            System.out.println("Unable to click on element: " + e.getMessage());
            logger.error("unable to click an Element");
        }
    }

    //Method to enter text into an input field
    public void enterText(By by, String value) {
        try {
            waitForElementToBeVisible(by);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
            logger.info("Entered text :" + getElementDescription(by) + " " + value);
        } catch (Exception e) {
            logger.error("Unable to enter the value: " + e.getMessage());
        }
    }

    //Method to get text from an input field
    public String getText(By by) {
        try {
            waitForElementToBeVisible(by);
            return driver.findElement(by).getText();
        } catch (Exception e) {
            logger.error("Unable to get text from element: " + e.getMessage());
            return "";
        }
    }

    //Method to Compare Two text

    public boolean compareText(By by, String expectedText) {
        try {
            waitForElementToBeVisible(by);
            String actualText = driver.findElement(by).getText();
            if (actualText.equals(expectedText)) {
                logger.info("Test Passed - Text is Matching");
                return true;
            } else {
                logger.error("Test Failed - Text is not Matching");
                logger.info("Expected: " + expectedText);
                logger.info("Actual: " + actualText);
                return false;
            }
        } catch (Exception e) {
            logger.error("Unable to compare texts: " + e.getMessage());
        }
        return false;
    }

    //Method to check if an element is displayed
    public boolean isDisplayed(By by) {
        try {
            waitForElementToBeVisible(by);
            logger.info("Element is displayed: " + getElementDescription(by));
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            logger.error("Element is not displayed: " + e.getMessage());
            return false;
        }
    }

    //Method to wait for page load
    public void waitForPageLoad(int timeOutInSec) {
        try {
            wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(
                    webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));
            logger.info("Page loaded successfully within " + timeOutInSec + " seconds.");
        } catch (Exception e) {
            logger.error("Page did not load within " + timeOutInSec + " seconds: " + e.getMessage());
        }
    }

    //Scroll to an Element
    public void scrollToElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            waitForElementToBeVisible(by);
            WebElement element = driver.findElement(by);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            logger.error("Unable to scroll to element: " + e.getMessage());
        }
    }

    //Wait for an element to be clickable
    private void waitForElementToBeClickable(By by) {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            logger.error("Element is not clickable: " + e.getMessage());
        }
    }

    //Wait for element to be visible
    private void waitForElementToBeVisible(By by) {
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logger.error("Element is not visible: " + e.getMessage());
        }
    }

    //method to get the description of an element using By locator

    public String getElementDescription(By locator) {
        //check for null driver or locator to avoid NullPointerException
        if (driver == null) {
            return "Driver is null";

        }
        if (locator == null) {
            return "Locator is null";
        }
        try {
            //find the element using the provided locator
            WebElement element = driver.findElement(locator);

            //get Element attributes
            String name = element.getDomAttribute("name");
            String id = element.getDomAttribute("id");
            String text = element.getText();
            String className = element.getAttribute("class");
            String placeholder = element.getAttribute("placeholder");

            //Return the description
            if (isNotEmpty(name)) {
                return "Element with name: " + name;
            } else if (isNotEmpty(id)) {
                return "Element with id: " + id;
            } else if (isNotEmpty(text)) {
                return "Element with text: " + truncate(text, 50);
            } else if (isNotEmpty(className)) {
                return "Element with class: " + className;
            } else if (isNotEmpty(placeholder)) {
                return "Element with placeholder: " + placeholder;
            }

        } catch (Exception e) {
            logger.error("Unable to get element description: " + e.getMessage());
//            return "Error retrieving element description";
        }
        return "unable to describe the element";
    }

    //Utitility method to check a string is not null or empty
    private boolean isNotEmpty(String value){
        return value != null && !value.trim().isEmpty();
    }

    //utility method to truncate long String
    private String truncate(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        return value.length() <= maxLength ? value : value.substring(0, maxLength) + "...";
    }
}

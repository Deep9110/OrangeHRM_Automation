package com.orangehrm.base;
import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.LoggerManager;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;


public class BaseClass {
    protected static Properties prop;
    protected static WebDriver driver;
    private static ActionDriver actionDriver;

    //initialize the looger here present in loggermanager class
    public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

    @BeforeSuite
    public void loadConfig() throws IOException {
        // Initialize properties and WebDriver here and load configuration file
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);
        logger.info("Config.properties file loaded successfully.");
    }

    @BeforeMethod
    public void setup() throws IOException {
        logger.info("Setting up Webdriver for: "+ this.getClass().getSimpleName());
        lunchBrowser();
        ConfigureBrowser();
        staticWait(2);

        logger.info("WebDriver initialized and browser maximized successfully.");
        logger.info("ActionDriver instance is created.");
        logger.trace("This is Trace Message");
        logger.debug("This is debug Message");
        logger.fatal("This is fatal Message");
        logger.warn("This is warn Message");

        //initialize the actionDriver only once
        if(actionDriver == null) {
            actionDriver = new ActionDriver(driver);
            logger.info("ActionDriver instance is created. " + Thread.currentThread().getId());

        }
    }
    private void lunchBrowser() {
        //initialize webdriver based on browser defined in config.properties file
        String browser = prop.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            // Initialize ChromeDriver
            driver = new ChromeDriver();
            logger.info("ChromeDriver Instance is created.");
        } else if (browser.equalsIgnoreCase("firefox")) {
            // Initialize FirefoxDriver
            driver = new FirefoxDriver();
            logger.info("Firefox Instance is created.");
        } else if (browser.equalsIgnoreCase("edge")) {
            // Initialize EdgeDriver
            driver = new EdgeDriver();
            logger.info("EdgeDriver Instance is created.");
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }

    //Configure browser settings like implicit wait, maximize window, and navigate to URL
    private void ConfigureBrowser(){
        //Implicit wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(implicitWait));

        //maximize the driver
        driver.manage().window().maximize();

        //Navigate to the URL defined in config.properties file

        try {
            String url = prop.getProperty("url");
            driver.get(url);
        } catch (Exception e) {
            logger.error("Failed to Navigate to the URl: " + e.getMessage());
        }
    }


    //quit the driver after test execution
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Error while quitting the driver: " + e.getMessage());
            }
        }
        logger.info("WebDriver instance is closed");
        driver = null; // Reset the driver to null after quitting
        actionDriver = null; // Reset the actionDriver to null after quitting
    }

    //Getter method for Properties object to access configuration properties in other classes
    public static Properties getProp() {
        return prop;
    }

    //Driver getter method to access the driver instance in other classes
    public static WebDriver getDriver() {
        if(driver==null){
           System.out.println("Webdriver is not initialized.");
           throw new IllegalStateException("WebDriver is not initialized.");
        }
        return driver;
    }

    //Getter method for actionDriver
    public static ActionDriver getActionDriver() {
        if(actionDriver==null){
            System.out.println("ActionDriver is not initialized.");
            throw new IllegalStateException("ActionDriver is not initialized.");
        }
        return actionDriver;
    }

    //Driver setter method to set the driver instance in other classes
    public void setDriver(WebDriver driver) {
        this.driver = driver;

    }

    //Static wait for pause
    public void staticWait(int seconds){
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds)); // 1 second wait
    }
}

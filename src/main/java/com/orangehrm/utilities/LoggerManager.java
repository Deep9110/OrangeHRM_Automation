package com.orangehrm.utilities;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoggerManager {

    //create a static method so that we can call using the class name
    public static Logger getLogger(Class<?> cls) {
        return LogManager.getLogger();
    }
}

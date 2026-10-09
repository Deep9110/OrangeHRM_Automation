package com.orangehrm.test;

import com.orangehrm.base.BaseClass;
import org.testng.annotations.Test;

public class DummyClass extends BaseClass {

    @Test
    public void dummyTest() {
        String title = driver.getTitle();

        assert title.equals("OrangeHRM") : "Title does not match!";
        System.out.println("Page title is: " + title);
        System.out.println("Test Passed - Title is Matching");
    }
}

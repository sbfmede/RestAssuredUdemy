package com.herokupp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class PositiveTest {

    @Test
    public void loginTest() {
        //Create driver
        System.setProperty("webdriver.chrome.driver","target/classes/chromedriver.exe");
        System.out.println("Starting test");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        var url="http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");

        driver.quit();
        }

//        open test page
//                enter username
//                        enter password
//                                click login button
//        verification:
//        new url
//                logout button is visible
//                successful login message

    }


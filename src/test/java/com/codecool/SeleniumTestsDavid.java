package com.codecool;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class SeleniumTestsDavid {

    private WebDriver driver;

    @BeforeAll
    public static void setUpClass(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
    }
    @AfterEach
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }

    //Login - Dávid

    //Logout - Dávid

    //Browse Projects - Gergő

    //Create Issue - TBD

    //Browse Issue - TBD

    //Edit Issue - TBD

}
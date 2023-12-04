package com.codecool;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

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
    @Test
    public void Login(){
        driver.navigate().to("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        var userName = driver.findElement(By.id("login-form-username"));
        var password = driver.findElement(By.id("login-form-password"));

        userName.sendKeys("automation66");
        password.sendKeys("CCAutoTest19.");

        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
    }

    //Logout - Dávid
    @Test
    public void LogOut() throws InterruptedException {
        driver.navigate().to("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
        Login();
        Thread.sleep(5000); // crlr + c megszakít (jobb megoldás a wait de ez jövő hét)
        var userProfil = driver.findElement(By.id("user-options"));
        userProfil.click();
        Thread.sleep(5000);
        var logoutButton = userProfil.findElement(By.xpath("//*[@id=\"log_out\"]"));
        logoutButton.click();
    }

    //Browse Projects - Gergő

    //Create Issue - TBD

    //Browse Issue - TBD

    //Edit Issue - TBD

}
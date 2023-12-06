package com.codecool;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class SeleniumTestsGergo {
    private WebDriver driver;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.navigate().to("https://jira-auto.codecool.metastage.net/secure/Dashboard.jspa");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    //Login - Dávid
    @Test
    public void LogIn() {
        var userName = driver.findElement(By.id("login-form-username"));
        var password = driver.findElement(By.id("login-form-password"));

        userName.sendKeys("automation66");
        password.sendKeys("CCAutoTest19.");

        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
    }

    //ujratölti az oldalt így azt meg kelle nézni miért
    /*@Test
    public void WrongPassword() throws InterruptedException {
        var userName = driver.findElement(By.id("login-form-username"));
        var password = driver.findElement(By.id("login-form-password"));

        userName.sendKeys("admin");
        password.sendKeys("admin.");
        Thread.sleep(2000);

        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
        Thread.sleep(2000);

        WebElement WarningMessage = driver.findElement(By.className("aui-message aui-message-error"));
        WarningMessage.isDisplayed();
    }*/

    //Logout - Dávid
    @Test
    public void LogOut() throws InterruptedException {
        LogIn();
        Thread.sleep(5000); // crlr + c megszakít (jobb megoldás a wait de ez jövő hét)
        var userProfil = driver.findElement(By.id("user-options"));
        userProfil.click();
        Thread.sleep(5000);
        var logoutButton = userProfil.findElement(By.xpath("//*[@id=\"log_out\"]"));
        logoutButton.click();
    }

    //Browse Projects - Gergő

    @Test
    public void browseProjects() throws InterruptedException {
        LogIn();
        Thread.sleep(5000);
        WebElement projectsNavbar = driver.findElement(By.xpath("/html/body/div[1]/header/nav/div/div[1]/ul/li[2]/a"));
        projectsNavbar.click();

        Thread.sleep(5000);

        WebElement openProject = driver.findElement(By.xpath("/html/body/div[1]/header/nav/div/div[1]/ul/li[2]/div/div[2]/ul/li[1]/a"));
        openProject.click();
        Thread.sleep(5000);
    }

    //Create Issue - Dávid
    @Test
    public void CreateIssue() throws InterruptedException {
        LogIn();
        Thread.sleep(3000);

        String[] Projects = {"COALA project (COALA)", "Demo (DEMO)", "JETI project (JETI)"};
        String[] Issues = {"Bug", "Epic", "Task", "Story"};

        for (String project : Projects) {
            for (String issue : Issues) {
                try {
                    WebElement CreateButton = driver.findElement(By.id("create_link"));
                    CreateButton.click();
                    Thread.sleep(2000);

                    WebElement SelectProject = driver.findElement(By.xpath("//*[@id=\"project-field\"]"));
                    SelectProject.click();
                    Thread.sleep(1000);
                    SelectProject.sendKeys(Keys.BACK_SPACE);
                    SelectProject.sendKeys(project);
                    SelectProject.sendKeys(Keys.ENTER);
                            /*var changeProjectType = SelectProject.findElement(By.xpath("//*[@id=\"coala-project-(coala)-547\"]/a"));
                            changeProjectType.click();*/
                    Thread.sleep(1000);

                    WebElement SelectIssue = driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]"));
                    SelectIssue.click();
                    Thread.sleep(1000);
                    SelectIssue.sendKeys(Keys.BACK_SPACE);
                    SelectIssue.sendKeys(issue);
                    SelectIssue.sendKeys(Keys.ENTER);
                    Thread.sleep(1000);

                    WebElement Summary = driver.findElement(By.xpath("//*[@id=\"summary\"]"));
                    Summary.sendKeys("This test created by automated testing.");
                    Thread.sleep(1000);

                    WebElement createButton = driver.findElement(By.xpath("//*[@id=\"create-issue-submit\"]"));
                    createButton.click();
                } catch (Exception e) { // A click nem fut le ezért hibára fut
                    WebElement CancelButton = driver.findElement(By.xpath("//*[@id=\"create-issue-dialog\"]/footer/div/div/button"));
                    CancelButton.click();
                }
            }
        }
    }

    //Browse Issue - TBD

    //Edit Issue - TBD

}


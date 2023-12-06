package com.codecool;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

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

    @Test
    public void noCaptchaAfterThirdTry() throws InterruptedException {

        for (int i = 0; i < 3; i++){
            var userName = driver.findElement(By.id("login-form-username"));
            var password = driver.findElement(By.id("login-form-password"));
            userName.sendKeys("asd");
            password.sendKeys("asd");

            Thread.sleep(5000);

            WebElement loginButton = driver.findElement(By.id("login"));
            loginButton.click();

            Thread.sleep(5000);
        }
    }

    @Test
    public void cantLoginWithoutCredentials() throws InterruptedException {
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();

        Thread.sleep(5000);

        WebElement errorMessage = driver.findElement(By.id("usernameerror"));
        var expectedResult = errorMessage.isDisplayed();

        Assertions.assertTrue(expectedResult);
    }

    @Test
    public void cantLoginWithoutCorrectCredentials() throws InterruptedException {

        var correctUserName = "automation66";
        var correctPassword = "CCAutoTest19.";
        var dummyUserName = "asd";
        var dummyPassword = "asd";
        var userNameField = driver.findElement(By.id("login-form-username"));
        var passwordField = driver.findElement(By.id("login-form-password"));
        WebElement loginButton = driver.findElement(By.id("login"));

        Thread.sleep(5000);

        userNameField.sendKeys(dummyUserName);
        passwordField.sendKeys(correctPassword);

        Thread.sleep(5000);

        loginButton.click();
        Thread.sleep(5000);

        WebElement errorMessage = driver.findElement(By.id("usernameerror"));
        var expectedResult = errorMessage.isDisplayed();

        Assertions.assertTrue(expectedResult);
    }

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

    @Test
    public void browseOtherProjects() throws InterruptedException {
        LogIn();
        Thread.sleep(5000);
        WebElement projectsNavbar = driver.findElement(By.xpath("/html/body/div[1]/header/nav/div/div[1]/ul/li[2]/a"));
        projectsNavbar.click();

        Thread.sleep(5000);

        List<WebElement> openProjects = driver.findElements(By.className("aui-icon-container"));

        Thread.sleep(5000);

        //find out a way to open every project
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

    //Browse Issue - Gergő

    @Test
    public void canBrowseIssue() throws InterruptedException {
        LogIn();
        Thread.sleep(5000);
        WebElement issueNavbar = driver.findElement(By.xpath("/html/body/div[1]/header/nav/div/div[1]/ul/li[3]/a"));
        issueNavbar.click();

        Thread.sleep(5000);

        WebElement searchForIssuesButton = driver.findElement(By.id("issues_new_search_link"));
        searchForIssuesButton.click();

        Thread.sleep(5000);

        WebElement selectProject = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/main/div/div[1]/form/div[1]/div[1]/div[1]/div[1]/div/div[1]/ul/li[1]/div[1]/div"));
        selectProject.click();

        Thread.sleep(7000);

        WebElement inputField = driver.findElement(By.id("searcher-pid-input"));
        inputField.sendKeys("COALA project");
        Thread.sleep(5000);
        inputField.sendKeys(Keys.ENTER);

        Thread.sleep(5000);

        inputField.sendKeys("JETI project");
        Thread.sleep(5000);
        inputField.sendKeys(Keys.ENTER);

        Thread.sleep(5000);

        //TOUACAN?
    }

    //Edit Issue - TBD

}


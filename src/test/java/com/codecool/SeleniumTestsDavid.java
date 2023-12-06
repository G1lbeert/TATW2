package com.codecool;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class SeleniumTestsDavid {
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

    //Create Issue - Dávid
    @Test
    public void CreateIssue() throws InterruptedException {
        LogIn();
        Thread.sleep(3000);

        String[] Projects = {"COALA project (COALA)", "JETI project (JETI)", "TOUCAN project (TOUCAN)"}; // Toucant nem választhatunk
        String[] Issues = {"Epic", "Bug", "Task", "Story"}; //Bug és Story az összesnél

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
                } catch (Exception e) {// A click nem fut le ezért hibára fut
                    e.getMessage();
                }
                WebElement CancelButton = driver.findElement(By.xpath("//*[@id=\"create-issue-dialog\"]/footer/div/div/button"));
                CancelButton.click();
            }
        }
    }

    @Test
    public void CreateIssueNegativeCase() throws InterruptedException {
        LogIn();
        Thread.sleep(3000);
        WebElement CreateButton = driver.findElement(By.id("create_link"));
        CreateButton.click();
        Thread.sleep(2000);

        WebElement SelectProject = driver.findElement(By.xpath("//*[@id=\"project-field\"]"));
        SelectProject.click();
        Thread.sleep(1000);
        SelectProject.sendKeys(Keys.BACK_SPACE);
        SelectProject.sendKeys("COALA project (COALA)");
        SelectProject.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        WebElement SelectIssue = driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]"));
        SelectIssue.click();
        Thread.sleep(1000);
        SelectIssue.sendKeys(Keys.BACK_SPACE);
        SelectIssue.sendKeys("Bug");
        SelectIssue.sendKeys(Keys.ENTER);
        Thread.sleep(1000);

        WebElement createButton = driver.findElement(By.xpath("//*[@id=\"create-issue-submit\"]"));
        createButton.click();
        Thread.sleep(1000);

        WebElement ErrorMessage = driver.findElement(By.xpath("//*[@id=\"dialog-form\"]/div/div[2]/div[1]/div"));
        var expectedResult = ErrorMessage.isDisplayed();

        Assertions.assertTrue(expectedResult);
    }

    //Browse Issue - TBD - Gergő

    //Edit Issue - TBD - Dávid
    @Test
    public void EditIssue() throws InterruptedException {
        // Tudom szerkeszteni a Toucan-t és a Jetit
        LogIn();
        Thread.sleep(1000);

        WebElement IssueButton = driver.findElement(By.id("find_link"));
        IssueButton.click();
        Thread.sleep(1000);

        WebElement SearchIssueButton = driver.findElement(By.id("issues_new_search_link_lnk"));
        SearchIssueButton.click();
        Thread.sleep(3000);

        WebElement SearchProjectDropDownMenu = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/form/div[1]/div[1]/div[1]/div[1]/div/div[1]/ul/li[1]/div/div/span"));
        SearchProjectDropDownMenu.click();
        Thread.sleep(1000);

        WebElement SearchField = driver.findElement(By.xpath("//*[@id=\"searcher-pid-input\"]"));
        SearchField.sendKeys("JETI project (JETI)");
        SearchField.sendKeys(Keys.ENTER);
        Thread.sleep(3000);

        WebElement OutClick = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div/div/div/div/div/div[1]/div[1]/div/div[1]"));
        OutClick.click();
        Thread.sleep(3000);

        WebElement ClickProject = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div/div/div/div/div/div[1]/div[1]/div/div[1]/div[2]/div/ol/li[1]/a/div/div[2]/span[1]"));
        ClickProject.click();
        Thread.sleep(1000);

        WebElement EditButton = driver.findElement(By.xpath("//*[@id=\"edit-issue\"]"));
        EditButton.click();
        Thread.sleep(3000);

        WebElement Summary = driver.findElement(By.xpath("//*[@id=\"summary\"]"));
        Summary.clear();
        Summary.sendKeys("This test is modified by automated test.");

        WebElement UpdateButton = driver.findElement(By.xpath("//*[@id=\"edit-issue-submit\"]"));
        UpdateButton.click();
    }

}
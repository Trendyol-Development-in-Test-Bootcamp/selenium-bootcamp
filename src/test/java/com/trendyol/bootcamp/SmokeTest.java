package com.trendyol.bootcamp;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class SmokeTest {

    /**
     * case 1
     * ------
     * 1- browseri actim
     * 2- www.trendyol.com'a gittim
     * 3- popupu kapatmak gerekecek
     * 4- searchbar'a telefon yazdim
     * 5- entera bastim
     * 6- sayfanin ust kisminda telefon yazdigini kontrol ettim
     * <p>
     * user actions
     * ------
     * click
     * input
     * scroll
     * url'e gider
     * hover yapabilir
     * surukle birak (click)
     * speech recognition
     * read
     * *
     */
    WebDriver webDriver;

    @BeforeMethod
    public void startUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        System.setProperty("webdriver.chrome.driver", "/Users/taylan.derinbay/Downloads/chromedriver");
        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://www.trendyol.com");

        webDriver.findElement(By.className("fancybox-close"))
                .click();
    }

    @Test
    public void shouldSearch() {
        webDriver.findElement(By.className("search-box")).sendKeys("telefon");
        webDriver.findElement(By.className("search-box")).sendKeys(Keys.ENTER);

        String resultText = webDriver.findElement(By.cssSelector(".dscrptn > h1")).getText();
//      By.xpath(//*[@data-id='dscrptn']/h1"))

        assertEquals(resultText, "telefon");
    }

    @Test
    public void shouldDisplayRecommendationsOnSearchBox() {
        webDriver.findElement(By.className("search-box")).sendKeys("telefon");
        boolean searchRecommendations = webDriver.findElement(By.className("suggestion-title")).isDisplayed();
        assertEquals(searchRecommendations, true);
    }

    @Test
    public void shouldLogin() {
        webDriver.findElement(By.className("account-user")).click();
        webDriver.findElement(By.id("login-email")).sendKeys("uatbuyer21@mailinator.com");
        webDriver.findElement(By.id("login-password-input")).sendKeys("1234qwe");
        webDriver.findElement(By.className("submit")).click();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("component-list")));

        String accountButtonText = webDriver.findElement(By.className("account-user")).getText();
        assertEquals(accountButtonText, "Hesabım");
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }

    //TODO: create page objects
    //TODO: webdrivermaneger (handle webdrivers)
    //TODO: create base classes
}

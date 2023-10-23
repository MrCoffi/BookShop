package com.example.MyBookShopApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainPageSeleniumTests {
    private static ChromeDriver driver;


    @BeforeAll
    static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\coffe\\chromedriver-win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    public void testMainPageAccess() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage
                .callPage()
                .pause();
        assertTrue(driver.getPageSource().contains("BOOKSHOP"));
    }

    @Test
    public void testMainPageSearchByQuery() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage
                .callPage()
                .pause()
                .setUpSearchToken("Whoregasm")
                .pause()
                .submit()
                .pause();
        assertTrue(driver.getPageSource().contains("Whoregasm"));
    }

    @Test
    public void testAllMenuItems() throws InterruptedException {
        List<String> menuPageList = new ArrayList<>();
        menuPageList.add("http://localhost:8085/genres");
        menuPageList.add("http://localhost:8085/");
        menuPageList.add("http://localhost:8085/books/recent");
        menuPageList.add("http://localhost:8085/books/popular");
        menuPageList.add("http://localhost:8085/authors");
        List<String> xpathListUrl = new ArrayList<>();
        xpathListUrl.add("//*[@id=\"navigate\"]/ul/li[2]/a");
        xpathListUrl.add("//*[@id=\"navigate\"]/ul/li[1]/a");
        xpathListUrl.add("//*[@id=\"navigate\"]/ul/li[3]/a");
        xpathListUrl.add("//*[@id=\"navigate\"]/ul/li[4]/a");
        xpathListUrl.add("//*[@id=\"navigate\"]/ul/li[5]/a");
        MainPage mainPage = new MainPage(driver);
        for (int i = 0; i < xpathListUrl.size(); i++) {
            for (String xpathPage : xpathListUrl) {
                driver.get(menuPageList.get(i));
                mainPage
                        .callPage()
                        .pause();
                driver.findElement(By.xpath(xpathPage)).click();
                System.out.println("curr page: " +menuPageList.get(i) + " this's page:" + xpathPage);
                assertTrue(driver.getPageSource().contains("BOOKSHOP"));
                mainPage.pause();
                if (!xpathPage.equals(xpathListUrl.get(i))) {
                    driver.navigate().back();
                }
            }
        }
    }
}
package com.sparta.kd.web_test_framework.tests;

import com.sparta.kd.web_test_framework.pages.Website;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TestSetup {
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver.exe";
    private static ChromeDriverService service;
    private WebDriver webDriver;

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=");
        options.setImplicitWaitTimeout(Duration.of(10, SECONDS));
        return options;
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterAll
    static void afterAll() {
        service.stop();
    }

    @BeforeEach
    public void beforeEach() {
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
    }

    @AfterEach
    public void afterEach() {
        webDriver.quit();
    }

    public Website getWebsite(String url) {
        webDriver.get(url);
        return new Website(webDriver);
    }
}

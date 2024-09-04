package com.sparta.kd.web_test_framework.stepdefs;

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
    private static WebDriver webDriver;

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=");
        options.setImplicitWaitTimeout(Duration.of(10, SECONDS));
        return options;
    }

    public static void startChromeService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    public static void stopService() {
        service.stop();
    }

    public static void createWebDriver() {
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
    }

    public static void quitWebDriver() {
        webDriver.quit();
    }

    public static Website getWebsite(String url) {
        webDriver.get(url);
        return new Website(webDriver);
    }
}

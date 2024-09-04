package com.sparta.kd.web_test_framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver webDriver;

    private final By userNameField = new By.ByName("user-name");
    private final By passwordField = new By.ByName("password");
    private final By loginButton = new By.ById("login-button");
    private final By errorMessageContainer = new By.ByClassName("error-message-container");

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void enterUsername(String username) {
        webDriver.findElement(userNameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        webDriver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        webDriver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        return webDriver.findElement(errorMessageContainer).findElement(By.cssSelector("h3")).getText();
    }

}

package com.sparta.kd.web_test_framework.tests;

import com.sparta.kd.web_test_framework.pages.Website;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

public class SwagLabsTests extends TestSetup {
    private static String BASE_URL = "https://www.saucedemo.com/";
    private Website website;

//    @Test
//    @DisplayName("Check that the webdriver works")
//    public void checkWebDriver() {
//        webDriver.get(BASE_URL);
//        Assertions.assertEquals(BASE_URL, webDriver.getCurrentUrl());
//        Assertions.assertEquals("Swag Labs", webDriver.getTitle());
//    }
//


//
//    @Test
//    @DisplayName("GIVEN I am logged in, WHEN I view the inventory page, THEN I should see the correct number of products")
//    public void checkNumProductsOnInventoryPage() throws IOException {
//        webDriver.get(BASE_URL);
//        WebElement username = webDriver.findElement(By.id("user-name"));
//        WebElement password = webDriver.findElement(By.id("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//
//        username.sendKeys("standard_user");
//        password.sendKeys("secret_sauce");
//        loginButton.click();
//
//        List<WebElement> products = webDriver.findElements(By.className("inventory_item"));
//        int numProducts = products.size();
//
//        try(PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
//            for (WebElement product : products) {
//                WebElement nameElement = product.findElement(By.className("inventory_item_name"));
//                WebElement priceElement = product.findElement(By.className("inventory_item_price"));
//                String itemInfo = nameElement.getText() + ": " + priceElement.getText();
//                writer.println(itemInfo);
//                System.out.println(itemInfo);
//            }
//        }
//
//        MatcherAssert.assertThat(numProducts, Matchers.is(6));
//    }
//

//
//    @Test
//    @DisplayName("GIVEN that I am on the droppable page, WHEN I drop an draggable into droppable, THEN I should see dropped text appears")
//    public void checkDroppedTextAppears() {
//        webDriver.get("https://demoqa.com/droppable/");
//
//        WebElement draggable = webDriver.findElement(By.id("draggable"));
//        WebElement droppable = webDriver.findElement(By.id("droppable"));
//
//        Actions actions = new Actions(webDriver);
//        actions.dragAndDrop(draggable, droppable).build().perform();
//
//        MatcherAssert.assertThat(droppable.getText(), Matchers.is("Dropped!"));
//    }
//
//    @Test
//    @DisplayName("GIVEN that I am on the products page, WHEN I click on an item link, THEN I should be navigated to that item's page")
//    public void clickingItemTakesYouToItemPage() {
//        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.of(10, SECONDS));
//        webDriver.get(BASE_URL);
//
//        WebElement username = webDriver.findElement(By.id("user-name"));
//        WebElement password = webDriver.findElement(By.id("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//
//        username.sendKeys("standard_user");
//        password.sendKeys("secret_sauce");
//        loginButton.click();
//
//        WebElement firstItem = webDriver.findElement(By.id("item_4_title_link"));
//        firstItem.click();
//
//        webDriverWait.until(driver -> driver.getCurrentUrl().contains("/inventory-item"));
//        WebElement itemName = webDriver.findElement(By.className("inventory_item_container"));
//
//        // No need to find all chained child elements, just the one you're looking for (I would assume it will scan all children and sub-children until it reaches)
//        MatcherAssert.assertThat(itemName
//                .findElement(By.className("inventory_details_img"))
//                .getAttribute("alt"), Matchers.is("Sauce Labs Backpack"));
//
//    }
//
//    @Test
//    @DisplayName("GIVEN I am on the products page, WHEN I click the cart, THEN I should be navigated to the cart page")
//    public void clickingCartTakesYouToCartPage() {
//        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.of(10, SECONDS));
//        webDriver.get(BASE_URL);
//
//        WebElement username = webDriver.findElement(By.id("user-name"));
//        WebElement password = webDriver.findElement(By.id("password"));
//        WebElement loginButton = webDriver.findElement(By.id("login-button"));
//
//        username.sendKeys("standard_user");
//        password.sendKeys("secret_sauce");
//        loginButton.click();
//
//        WebElement cartLink = webDriver.findElement(By.className("shopping_cart_link"));
//        cartLink.click();
//
//        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL + "cart.html"));
//
//    }

    @Test
    @DisplayName("GIVEN I enter a valid username and password, WHEN I click login, THEN I should land on the inventory page")
    public void successfulLogin() {
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("secret_sauce");
        website.getHomePage().clickLogin();

        MatcherAssert.assertThat(website.getCurrentUrl(), Matchers.is(BASE_URL + "inventory.html"));
    }

    @Test
    @DisplayName("GIVEN that I am on the login page, WHEN I enter an invalid password, THEN I should see an error message containing epic sadface")
    public void checkInvalidPassErrorContainsEpicSadface() {
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("invalid_password");
        website.getHomePage().clickLogin();

        MatcherAssert.assertThat(website.getHomePage().getErrorMessage(), Matchers.containsString("Epic sadface"));
    }

    @Test
    @DisplayName("GIVEN that I am on the products page, WHEN I count the number of products, THEN I should see 6")
    public void checkNumProducts() {
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("secret_sauce");
        website.getHomePage().clickLogin();

        int numProducts = website.getInventoryPage().getItems().size();

        MatcherAssert.assertThat(numProducts, Matchers.is(6));
    }

    @Test
    @DisplayName("GIVEN that I am on the product page, WHEN I add an item to the cart, THEN I should see the number in the cart increase by 1 OR go to 1 if cart was empty")
    public void checkCartIncrementsUponAddition() {
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("secret_sauce");
        website.getHomePage().clickLogin();

        int cartCount = website.getInventoryPage().getCartCount();
        website.getInventoryPage().addToCart(website.getInventoryPage().getItems().getFirst());
        int newCartCount = website.getInventoryPage().getCartCount();

        MatcherAssert.assertThat(newCartCount, Matchers.is(cartCount + 1));
    }
}


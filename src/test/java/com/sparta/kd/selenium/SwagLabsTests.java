package com.sparta.kd.selenium;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import java.util.Set;

import static java.time.temporal.ChronoUnit.SECONDS;

public class SwagLabsTests {
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver.exe";
    private static String BASE_URL = "https://www.saucedemo.com/";
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

    @Test
    @DisplayName("Check that the webdriver works")
    public void checkWebDriver() {
        webDriver.get(BASE_URL);
        Assertions.assertEquals(BASE_URL, webDriver.getCurrentUrl());
        Assertions.assertEquals("Swag Labs", webDriver.getTitle());
    }

    @Test
    @DisplayName("GIVEN I enter a valid username and password, WHEN I click login, THEN I should land on the inventory page")
    public void successfulLogin() {
        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.of(10, SECONDS));

        webDriver.get(BASE_URL);
        WebElement username = webDriver.findElement(By.id("user-name"));
        WebElement password = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginButton.click();
        webDriverWait.until(driver -> driver.getCurrentUrl().contains("/inventory"));

        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL + "inventory.html"));
    }

    @Test
    @DisplayName("GIVEN I am logged in, WHEN I view the inventory page, THEN I should see the correct number of products")
    public void checkNumProductsOnInventoryPage() throws IOException {
        webDriver.get(BASE_URL);
        WebElement username = webDriver.findElement(By.id("user-name"));
        WebElement password = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginButton.click();

        List<WebElement> products = webDriver.findElements(By.className("inventory_item"));
        int numProducts = products.size();

        try(PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            for (WebElement product : products) {
                WebElement nameElement = product.findElement(By.className("inventory_item_name"));
                WebElement priceElement = product.findElement(By.className("inventory_item_price"));
                String itemInfo = nameElement.getText() + ": " + priceElement.getText();
                writer.println(itemInfo);
                System.out.println(itemInfo);
            }
        }

        MatcherAssert.assertThat(numProducts, Matchers.is(6));
    }

    @Test
    @DisplayName("GIVEN that I am on the login page, WHEN I enter an invalid password, THEN I should see an error message containing epic sadface")
    public void checkInvalidPassErrorContainsEpicSadface() {
        webDriver.get(BASE_URL);

        WebElement username = webDriver.findElement(By.id("user-name"));
        WebElement password = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        username.sendKeys("standard_user");
        password.sendKeys("invalid_password");
        loginButton.click();

        WebElement error = webDriver.findElement(By.className("error-message-container"));

        MatcherAssert.assertThat(error.getText(), Matchers.containsString("Epic sadface"));
    }

    @Test
    @DisplayName("GIVEN that I am on the droppable page, WHEN I drop an draggable into droppable, THEN I should see dropped text appears")
    public void checkDroppedTextAppears() {
        webDriver.get("https://demoqa.com/droppable/");

        WebElement draggable = webDriver.findElement(By.id("draggable"));
        WebElement droppable = webDriver.findElement(By.id("droppable"));

        Actions actions = new Actions(webDriver);
        actions.dragAndDrop(draggable, droppable).build().perform();

        MatcherAssert.assertThat(droppable.getText(), Matchers.is("Dropped!"));
    }

    @Test
    @DisplayName("GIVEN that I am on the products page, WHEN I click on an item link, THEN I should be navigated to that item's page")
    public void clickingItemTakesYouToItemPage() {
        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.of(10, SECONDS));
        webDriver.get(BASE_URL);

        WebElement username = webDriver.findElement(By.id("user-name"));
        WebElement password = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginButton.click();

        WebElement firstItem = webDriver.findElement(By.id("item_4_title_link"));
        firstItem.click();

        webDriverWait.until(driver -> driver.getCurrentUrl().contains("/inventory-item"));
        WebElement itemName = webDriver.findElement(By.className("inventory_item_container"));

        // No need to find all chained child elements, just the one you're looking for (I would assume it will scan all children and sub-children until it reaches)
        MatcherAssert.assertThat(itemName
                .findElement(By.className("inventory_details_img"))
                .getAttribute("alt"), Matchers.is("Sauce Labs Backpack"));

    }

    @Test
    @DisplayName("GIVEN I am on the products page, WHEN I click the cart, THEN I should be navigated to the cart page")
    public void clickingCartTakesYouToCartPage() {
        Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.of(10, SECONDS));
        webDriver.get(BASE_URL);

        WebElement username = webDriver.findElement(By.id("user-name"));
        WebElement password = webDriver.findElement(By.id("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginButton.click();

        WebElement cartLink = webDriver.findElement(By.className("shopping_cart_link"));
        cartLink.click();

        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL + "cart.html"));

    }
}


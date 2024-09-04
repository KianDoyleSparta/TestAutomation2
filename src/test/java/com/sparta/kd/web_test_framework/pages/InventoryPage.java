package com.sparta.kd.web_test_framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InventoryPage {
    private WebDriver webDriver;

    private final By item = new By.ByClassName("inventory_item");
    private final By button = new By.ByTagName("button");
    private final By cart = new By.ById("shopping_cart_container");
    private final By cartBadge = new By.ByClassName("shopping_cart_badge");

    public InventoryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public List<WebElement> getItems() {
        return webDriver.findElements(item);
    }

    public WebElement getCart() {
        return webDriver.findElement(cart);
    }

    public int getCartCount() {
        return getCart().findElements(cartBadge).isEmpty() ? 0 : Integer.parseInt(getCart().findElement(cartBadge).getText());
    }

    public void addToCart(WebElement item) {
        item.findElement(button).click();
    }

}

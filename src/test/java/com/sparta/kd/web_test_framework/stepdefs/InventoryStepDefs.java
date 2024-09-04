package com.sparta.kd.web_test_framework.stepdefs;

import com.sparta.kd.web_test_framework.pages.Website;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebElement;

import java.util.List;

public class InventoryStepDefs {

    private Website website;
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private int numItems;
    private int numCartItems;

    @Given("I am on the inventory page")
    public void iAmOnTheInventoryPage() {
        website = TestSetup.getWebsite(BASE_URL + "inventory.html");
    }

    @When("I count the number of items")
    public void iCountTheNumberOfItems() {
        List<WebElement> items = website.getInventoryPage().getItems();
        numItems = items.size();
    }

    @Then("I should see the number of items is {string}")
    public void iShouldSeeTheNumberOfItemsIs(String expectedNumberOfItems) {
        MatcherAssert.assertThat(numItems, Matchers.is(Integer.parseInt(expectedNumberOfItems)));
    }

    @When("I add an item to my cart")
    public void iAddAnItemToMyCart() {
        numCartItems = website.getInventoryPage().getCartCount();
        website.getInventoryPage().addToCart(website.getInventoryPage().getItems().get(0));
    }

    @Then("I should see the number of items in the cart increment by {string}")
    public void iShouldSeeTheNumberOfItemsInTheCartIncrementBy(String expectedIncrement) {
        int newCartItems = website.getInventoryPage().getCartCount();
        MatcherAssert.assertThat(newCartItems, Matchers.is(numCartItems + Integer.parseInt(expectedIncrement)));
    }
}

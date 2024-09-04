package com.sparta.kd.web_test_framework.stepdefs;

import com.sparta.kd.web_test_framework.pages.Website;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.io.IOException;

public class LoginStepDefs {

    private Website website;

    private static final String BASE_URL = "https://www.saucedemo.com/";

    @After
    public void afterEach () {
        TestSetup.createWebDriver();
        TestSetup.stopService();
    }

    @Before
    public void setup () throws IOException {
        TestSetup.startChromeService();
        TestSetup.createWebDriver();
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        website = TestSetup.getWebsite(BASE_URL);
    }

    @And("I have entered the username {string}")
    public void iHaveEnteredTheUsername(String username) {
        website.getHomePage().enterUsername(username);
    }

    @And("I have entered the password {string}")
    public void iHaveEnteredThePassword(String password) {
        website.getHomePage().enterPassword(password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        website.getHomePage().clickLogin();
    }

    @Then("I should land on the inventory page")
    public void iShouldLandOnTheInventoryPage() {
        MatcherAssert.assertThat(website.getCurrentUrl(), Matchers.containsString("inventory.html"));
    }

    @Then("I should see an error message that contains {string}")
    public void iShouldSeeAnErrorMessageThatContains(String expected) {
        MatcherAssert.assertThat(website.getHomePage().getErrorMessage(), Matchers.containsString(expected));
    }
}

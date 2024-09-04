Feature: Inventory

  In order to add items to my cart
  As a logged in user
  I should be able to see all items available

  Background:
    Given I am on the home page
    And I have entered the username "standard_user"
    And I have entered the password "secret_sauce"
    When I click the login button
    Then I should land on the inventory page

  @Happy
  Scenario: Logged in users should see a list of items
    Given I am on the inventory page
    When I count the number of items
    Then I should see the number of items is "6"

  @Happy
  Scenario: Adding an item to the cart should increase the cart count by 1
    Given I am on the inventory page
    When I add an item to my cart
    Then I should see the number of items in the cart increment by "1"
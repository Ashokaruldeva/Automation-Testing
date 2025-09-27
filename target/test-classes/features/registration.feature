Feature: Registration functionality

  Scenario: Successful registration with valid details
    Given I am on the login page
    When I enter username "Admin" and password "admin123"
    And I click the login button
    Then I should be logged in successfully
    When I change to dark mode
    Then the theme should be changed to dark mode
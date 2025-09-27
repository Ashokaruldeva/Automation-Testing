Feature: Login functionality

  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter username "Admin" and password "admin123"
    And I click the login button
    Then I should be logged in successfully
    
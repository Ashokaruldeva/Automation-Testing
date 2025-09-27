Feature: Shopping functionality

  Scenario: Successful shopping cart operations
    Given I am on the login page
    When I enter username "Admin" and password "admin123"
    And I click the login button
    Then I should be logged in successfully
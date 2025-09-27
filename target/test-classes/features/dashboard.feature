Feature: Dashboard functionality

  Scenario: Successful dashboard navigation and operations
    Given I am on the login page
    When I enter username "Admin"
    And I enter password "admin123"
    And I click the login button
    Then I should be logged in successfully
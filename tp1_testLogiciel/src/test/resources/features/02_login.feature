@LoginTests
Feature: Authentication

  @ValidCredentials
  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters an email as "user74@example.com"
    And the user enters a password as "User72felabc_2025*/"
    And the user clicks the login button
    Then the user should be redirected to the home page

  @InvalidCredentials
  Scenario Outline: Failed login with invalid credentials
    Given the user is on the login page
    When the user enters an email as "<email>"
    And the user enters a password as "<password>"
    And the user clicks the login button
    Then the user should see an error message

    Examples:
      | email          | password   |
      | invalid@email  | wrongpass  |

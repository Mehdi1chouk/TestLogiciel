@LoginTests
Feature: Authentication

  @ValidCredentials
  Scenario Outline: Successful login
    Given the user is on the login page
    When the user enters a username as "<username>"
    And the user enters a password as "<password>"
    And clicks on the login button
    Then the user should see a successful login message

    Examples:
      | username  | password              |
      | tomsmith  | SuperSecretPassword!  |

  @InvalidCredentials
  Scenario Outline: Failed login
    Given the user is on the login page
    When the user enters a username as "<username>"
    And the user enters a password as "<password>"
    And clicks on the login button
    Then the user should see an error message

    Examples:
      | username    | password |
      | invaliduser | wrongpass|
      | admin       | 123456   |

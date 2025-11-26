Feature: Login Functionality
  @runValidLoginAsAdmin
  Scenario: Login with Valid Credentials
    Given the user is on the login page
    When the user enters a username
    And the user enters a password
    And clicks on the login button
    Then the user should see a successful login message
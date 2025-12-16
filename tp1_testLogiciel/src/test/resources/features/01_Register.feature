@RegisterTests
Feature: User Registration

  Background:
    Given the user is on the registration page


  @ValidCredentials
  Scenario: Registration succeeds with valid credentials
    And the user enters email as "user77@example.com"
    And the user enters first name as "usersevenseven"
    And the user enters last name as "lastname"
    And the user enters password as "User72felabc_2025*/"
    And clicks on the save button
    Then the registration should succeed


  @InvalidCredentials
  Scenario: Registration fails with no username
    And the user enters email as "userjdid77@example.com"
    And the user enters last name as "lastnamesix"
    And the user enters password as "Password123"
    And clicks on the save button
    Then the registration should fail and show error
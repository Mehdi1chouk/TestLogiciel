@CategoryNavigation
Feature: Discover categories

  @SuccessfulFiltering
  Scenario: Navigate through all activity categories
    Given the user is on the activity of catalog page
    Then take a debug screenshot and show the activities

    When the user clicks on the "All" category
    And the user should waits for 1 seconds

    And the user clicks on the "Watersports" category
    And the user should waits for 1 seconds

    And the user clicks on the "café/resto" category
    And the user should waits for 1 seconds

    And the user clicks on the "Monuments" category
    And the user should waits for 1 seconds

    And the user clicks on the "Adventure" category
    And the user should waits for 1 seconds

    And the user clicks on the "Authentic" category
    And the user should waits for 1 seconds

    And the user clicks on the "Traditional Food" category
    And the user should waits for 1 seconds

    And the user clicks on the "Activities" category
    And the user should waits for 1 seconds

    Then the user should see all categories loaded successfully


  @InvalidCategory
  Scenario: Attempt to filter with empty category
    Given the user is on the activity of catalog page
    When the user attempts to click on the "" category
    Then the category "" should not exist




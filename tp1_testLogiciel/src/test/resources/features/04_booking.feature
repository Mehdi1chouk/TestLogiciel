@BookingTests
Feature: Activity Booking Management

  @SuccessfulBooking
  Scenario: Successfully book a specific activity with valid details
    Given the user is on the login page
    When the user enters an email as "user74@example.com"
    And the user enters a password as "User72felabc_2025*/"
    And the user clicks the login button
    Then the user should be redirected to the home page
    And the user waits for 2 seconds
    And the user is on the activity catalog page
    Then take a debug screenshot and show activities
    When the user selects and books the "diving borjerras" activity
    And the user selects a future date
    And the user selects time "09:00"
    And the user selects number of guests "2"
    And the user clicks the reserve button
    And the user enters phone number as "23456789"
    And the user enters notes as "Looking forward to the experience"
    And the user selects payment method
    And the user clicks the confirm booking button
    Then the user should see booking confirmation modal
    And the booking reference should be displayed


  @InvalidPhone
  Scenario: Attempt to book with invalid phone format
    Given the user is on the login page
    When the user enters an email as "user74@example.com"
    And the user enters a password as "User72felabc_2025*/"
    And the user clicks the login button
    Then the user should be redirected to the home page
    And the user waits for 2 seconds
    And the user is on the activity catalog page
    Then take a debug screenshot and show activities
    When the user selects and books the "diving borjerras" activity
    And the user selects a future date
    And the user selects time "09:00"
    And the user selects number of guests "2"
    And the user clicks the reserve button
    And the user enters phone number as "abc"
    And the user enters notes as "Looking forward to the experience"
    And the confirm booking button should be disabled

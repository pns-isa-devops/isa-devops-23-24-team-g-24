Feature: Browsing different activities

  This feature simply browses the available activities

  Scenario: There is 2 activities
    Given I have 2 activities
    When I browse the activities
    Then I should see 2 activities in it

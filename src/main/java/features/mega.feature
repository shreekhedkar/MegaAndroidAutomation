@FunctionalTests
Feature: Test Mega android app

  @SanityTests
  Scenario: Create the text file
    Given launch mega android application
    And provide login credentials
    Then user is logged in
    And create text file in cloud drive
    
  @SanityTests
  Scenario: Delete the file
    Given mega android application
    When file is moved to rubbish bin
    Then file is deleted

  @SanityTests
  Scenario: Restore the file
    Given mega android application
    When file is restored from rubbish bin
    Then file is restored back to cloud drive
Feature: Trip Service

  Scenario: logged user is null
    Given logged user is null
    When get trips by user
    Then got this error
    """
    class.simpleName= UserNotLoggedInException
    """

  Scenario: logged user is not a friend
    Given logged user is not null
    And user has no friends
    When get trips by user
    Then trip list is
    """
    : []
    """

  Scenario: logged user is a friend
    Given logged user is not null
    And user has logged user as a friend
    And user has a trip
    When get trips by user
    Then trip list is
    """
    ::size= 1
    """

  Scenario: logged user is not a friend and user has friends
    Given logged user is not null
    And user has some friends
    When get trips by user
    Then trip list is
    """
    : []
    """

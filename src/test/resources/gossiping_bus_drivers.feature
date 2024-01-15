Feature: Gossiping Bus Drivers

  Scenario: 1 driver with 1 stop
    Given the following drivers with their route stops:
      | 1 |
    Then it should:
    """
    simulate= 1
    """

  Scenario: 2 drivers with 1 stop but never met
    Given the following drivers with their route stops:
      | 1 |
      | 2 |
    Then it should:
    """
    simulate::throw.class.simpleName: IllegalStateException
    """

  Scenario: 2 drivers with 1 stop and met
    Given the following drivers with their route stops:
      | 1 |
      | 1 |
    Then it should:
    """
    simulate= 1
    """

  Scenario: 2 drivers with 2 stops and met
    Given the following drivers with their route stops:
      | 1 2 |
      | 3 2 |
    Then it should:
    """
    simulate= 2
    """
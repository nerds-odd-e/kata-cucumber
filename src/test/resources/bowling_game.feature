Feature: Bowling Game

  Scenario: start game
    Then score is 0

  Rule: first frame

    Scenario Outline: roll 1-1 ball
      When roll
        | <pin> |
      Then score is <score>
      Examples:
        | pin | score |
        | 0   | 0     |
        | 5   | 5     |
        | 9   | 9     |
        | 10  | 10    |

    Scenario Outline: roll 1-2 ball when first frame not a strike
      When roll
        | 5 | <secondPin> |
      Then score is <score>
      Examples:
        | secondPin | score |
        | 3         | 8     |
        | 5         | 10    |

  Rule: second frame

    Scenario Outline: roll 2-1 ball when first frame is a spare
      When roll
        | 5 | 5 | <pin> |
      Then score is <score>
      Examples:
        | pin | score |
        | 0   | 10    |
        | 1   | 12    |
        | 9   | 28    |

    Scenario: roll 2-1 ball when first frame is a spare (0|10)
      When roll
        | 0 | 10 | 8 |
      Then score is 26

    Scenario Outline: roll 2-2 ball when first frame is a spare
      When roll
        | 5 | 5 | <pin> | 1 |
      Then score is <score>
      Examples:
        | pin | score |
        | 0   | 11    |
        | 5   | 21    |

    Scenario: roll 2-2 ball when first frame is a spare (0|10)
      When roll
        | 0 | 10 | 0 | 6 |
      Then score is 16

    Scenario Outline: roll 2-1 when first frame is a strike
      When roll
        | 10 | <pin> |
      Then score is <score>
      Examples:
        | pin | score |
        | 0   | 10    |
        | 1   | 12    |
        | 9   | 28    |
        | 10  | 30    |

    Scenario Outline: roll 2-2 when first frame is a strike
      When roll
        | 10 | 5 | <pin> |
      Then score is <score>
      Examples:
        | pin | score |
        | 4   | 28    |
        | 5   | 30    |

  Rule: third frame when first frame is a strike

    Background:
      Given roll
        | 10 |

    Scenario Outline: roll 3-1
      When roll
        | <firstPin> | <secondPin> | <thirdFramePin> |
      Then score is <score>
      Examples:
        | firstPin | secondPin | thirdFramePin | score |
        | 0        | 7         | 5             | 29    |
        | 5        | 5         | 3             | 36    |

    Scenario: roll 3-1 when first and second frame are both strikes
      When roll
        | 10 | 10 |
      Then score is 60

  Rule: last frames with spare or strike

    Background:
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |

    Scenario: last second frame is a strike
      When roll
        | 10 | 10 | 3 |
      Then score is 36

    Scenario: last frame is a strike
      When roll
        | 10 | 10 | 3 | 6 |
      Then score is 42

    Scenario: all X to the end
      When roll
        | 10 | 10 | 10 | 10 |
      Then score is 60

  Rule: acceptance tests

    Scenario: perfect game
      When roll
        | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 |
      Then score is 300

    Scenario: roll exception after perfect game
      When roll
        | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 |
      Then game should
      """
        roll[1]::throw
      """

  Rule: game over exception

    Background:
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |
      Given roll
        | 0 | 0 |

    Scenario: roll after normal last framework
      Given roll
        | 5 | 4 |
      Then game should
      """
        roll[1]::throw
      """

    Scenario: roll after spare last framework
      Given roll
        | 5 | 5 | 7 |
      Then game should
      """
        roll[1]::throw
      """

    Scenario: roll after strike last framework
      Given roll
        | 10 | 5 | 7 |
      Then game should
      """
        roll[1]::throw
      """

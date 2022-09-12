Feature: Bowling Game

  Scenario: start game
    Then score is 0

  Rule: first frame

    Scenario Outline: roll 1-1 ball
      Then game should
      """
        makeRoll[<pin>].score= <score>
      """
      Examples:
        | pin | score |
        | 0   | 0     |
        | 5   | 5     |
        | 9   | 9     |
        | 10  | 10    |

    Scenario Outline: roll 1-2 ball when first frame not a strike
      Then game should
      """
        makeRoll[5].makeRoll[<secondPin>].score= <score>
      """
      Examples:
        | secondPin | score |
        | 3         | 5+3   |
        | 5         | 5+5   |

  Rule: second frame

    Scenario Outline: roll 2-1 ball when first frame is a spare
      Then game should
      """
        makeRoll[5].makeRoll[5].
        makeRoll[<pin>].score= <score>
      """
      Examples:
        | pin | score     |
        | 0   | (5+5)+0   |
        | 1   | (5+5+1)+1 |
        | 9   | (5+5+9)+9 |

    Scenario: roll 2-1 ball when first frame is a spare (0|10)
      Then game should
      """
        makeRoll[0].makeRoll[10].
        makeRoll[8].score= (0+10+8)+8
      """

    Scenario Outline: roll 2-2 ball when first frame is a spare
      Then game should
      """
        makeRoll[5].makeRoll[5].
        makeRoll[<pin>].makeRoll[1].score= <score>
      """
      Examples:
        | pin | score         |
        | 0   | (5+5+0)+(0+1) |
        | 5   | (5+5+5)+(5+1) |

    Scenario: roll 2-2 ball when first frame is a spare (0|10)
      Then game should
      """
        makeRoll[0].makeRoll[10].
        makeRoll[0].makeRoll[6].score= (0+10+0)+(0+6)
      """

    Scenario Outline: roll 2-1 when first frame is a strike
      Then game should
      """
        makeRoll[10].
        makeRoll[<pin>].score= <score>
      """
      Examples:
        | pin | score      |
        | 0   | (10+0)+0   |
        | 1   | (10+1)+1   |
        | 9   | (10+9)+9   |
        | 10  | (10+10)+10 |

    Scenario Outline: roll 2-2 when first frame is a strike
      Then game should
      """
        makeRoll[10].
        makeRoll[5].makeRoll[<pin>].score= <score>
      """
      Examples:
        | pin | score          |
        | 4   | (10+5+4)+(5+4) |
        | 5   | (10+5+5)+(5+5) |

  Rule: third frame when first frame is a strike

    Background:
      Given roll
        | 10 |

    Scenario Outline: roll 3-1
      Then game should
      """
        makeRoll[<firstPin>].makeRoll[<secondPin>].
        makeRoll[<thirdFramePin>].score= <score>
      """
      Examples:
        | firstPin | secondPin | thirdFramePin | score              |
        | 0        | 7         | 5             | (10+0+7)+(0+7)+5   |
        | 5        | 5         | 3             | (10+5+5)+(5+5+3)+3 |

    Scenario: roll 3-1 when first and second frame are both strikes
      Then game should
      """
        makeRoll[10].
        makeRoll[10].score= 60
      """

  Rule: last frames with spare or strike

    Background: first 8 frames are all 0
      Given roll
        | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |

    Scenario: last second frame is a strike
      Then game should
      """
        makeRoll[10].
        makeRoll[10].
        makeRoll[3].score= 36
      """

    Scenario: last frame is a strike
      Then game should
      """
        makeRoll[10].
        makeRoll[10].
        makeRoll[3].makeRoll[6].score= 42
      """

    Scenario: all X to the end
      Then game should
      """
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].makeRoll[10].score= 60
      """

  Rule: acceptance tests

    Scenario: perfect game
      Then game should
      """
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].makeRoll[10].score= 300
      """

    Scenario: roll exception after perfect game
      Then game should
      """
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].
        makeRoll[10].makeRoll[10].makeRoll[10].
        roll[1]::throw
      """

  Rule: game over exception

    Background: first 9 frames are all 0
      Given roll
        | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |

    Scenario: roll after normal last framework
      Then game should
      """
        makeRoll[5].makeRoll[4].
        roll[1]::throw
      """

    Scenario: roll after spare last framework
      Then game should
      """
        makeRoll[5].makeRoll[5].makeRoll[7].
        roll[1]::throw
      """

    Scenario: roll after strike last framework
      Then game should
      """
        makeRoll[10].makeRoll[5].makeRoll[7].
        roll[1]::throw
      """

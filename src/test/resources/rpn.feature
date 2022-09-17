Feature: RPN

  Scenario: a single number
    Then rpn should be
    """
    calculate: {
      '10'= 10
      '42'= 42
    }
    """

  Scenario: addition, subtraction, multiplication, division
    Then rpn should be
    """
    calculate: {
      '4 2 +'= 4+2
      '7 3 -'= 7-3
      '5 2 *'= 5*2
      '6 2 /'= 6/2
    }
    """

  Scenario: two op
    Then rpn should be
    """
    calculate: {
      '4 2 + 5 +'= 4+2+5
    }
    """

  Scenario: sqrt
    Then rpn should be
    """
    calculate: {
      '4 sqrt'= 2
    }
    """

  Scenario: max
    Then rpn should be
    """
    calculate: {
      '4 max'= 4
      '7 3 max'= 7
      '7 3 4 max'= 7
    }
    """
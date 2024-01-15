Feature: diamond

  Scenario: diamond
    Then the diamond should:
    """
    diamond: {
      A: A

      B: ```
          A
         B B
          A
         ```

      C: ```
           A
          B B
         C   C
          B B
           A
         ```

      D: ```
            A
           B B
          C   C
         D     D
          C   C
           B B
            A
         ```
    }
    """
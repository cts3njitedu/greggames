Feature: Make sure spade player methods are working

  Scenario: Testing the determinePlayerWinner method
    Given a state of game, a temp winner and the current player and temp winner playing card
      | tempWinnerCardValue | tempWinnerSuit | currPlayerCardValue | currPlayerSuit | trickCount |
      | KING                | SPADES         | THREE               | SPADES         |          1 |
      | FIVE                | DIAMONDS       | THREE               | DIAMONDS       |          2 |
      | SIX                 | CLUBS          | EIGHT               | SPADES         |          4 |
      | SIX                 | CLUBS          | EIGHT               | CLUBS          |          4 |
      | SIX                 | CLUBS          | ACE                 | DIAMONDS       |          1 |
      | SIX                 | CLUBS          | SEVEN               | DIAMONDS       |          4 |
      | SIX                 | CLUBS          | THREE               | CLUBS          |          4 |
    When I determine who the temp winner is
    Then I should be successful

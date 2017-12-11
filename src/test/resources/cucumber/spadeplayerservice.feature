Feature: Make sure spade player methods are working

  Scenario: Testing the determinePlayerWinner method
    Given a state of game, a temp winner and the current player and temp winner playing card
      | tempWinnerCard | currPlayerCard | trickCount |
      | KING_SPADES    | THREE_SPADES   |          1 |
      | FIVE_DIAMONDS  | THREE_DIAMONDS |          2 |
      | SIX_CLUBS      | EIGHT_SPADES   |          4 |
      | SIX_CLUBS      | EIGHT_CLUBS    |          4 |
      | SIX_CLUBS      | ACE_DIAMONDS   |          1 |
      | SIX_CLUBS      | SEVEN_DIAMONDS |          4 |
      | SIX_CLUBS      | THREE_CLUBS    |          4 |
    When I determine who the temp winner is
    Then I should be successful
    @gettingPlayerCard
    Scenario: Testing gettingPlayerCard method
    Given a currplayer and tempwinner
    When I determine player card
    Then I should be correct in determining player card
    


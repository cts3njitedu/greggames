Feature: Testing bot methods

  @playingHand
  Scenario: Testing one entire hand
    Given a game and players cards for bot
      | player1       | player2        | player3        | player4        |
      | SIX_DIAMONDS  | FOUR_SPADES    | TWO_HEARTS     | THREE_CLUBS    |
      | SIX_CLUBS     | SEVEN_CLUBS    | THREE_DIAMONDS | KING_HEARTS    |
      | QUEEN_SPADES  | FOUR_CLUBS     | EIGHT_CLUBS    | FIVE_HEARTS    |
      | SIX_HEARTS    | EIGHT_SPADES   | NINE_SPADES    | TWO_CLUBS      |
      | TEN_CLUBS     | JACK_CLUBS     | SEVEN_SPADES   | JACK_SPADES    |
      | JACK_DIAMONDS | TWO_SPADES     | KING_CLUBS     | QUEEN_DIAMONDS |
      | SEVEN_HEARTS  | TEN_DIAMONDS   | TEN_HEARTS     | FOUR_HEARTS    |
      | JACK_HEARTS   | QUEEN_HEARTS   | FIVE_CLUBS     | ACE_SPADES     |
      | NINE_HEARTS   | SEVEN_DIAMONDS | ACE_DIAMONDS   | KING_DIAMONDS  |
      | THREE_HEARTS  | KING_SPADES    | ACE_CLUBS      | SIX_SPADES     |
      | ACE_HEARTS    | EIGHT_DIAMONDS | NINE_CLUBS     | NINE_DIAMONDS  |
      | TWO_DIAMONDS  | FOUR_DIAMONDS  | QUEEN_CLUBS    | FIVE_DIAMONDS  |
      | FIVE_SPADES   | EIGHT_HEARTS   | TEN_SPADES     | THREE_SPADES   |
    When I attempt to play the game for one hand
    Then I should be successful in playing the game

  @determineBotCard
  Scenario: Testing how to get bot card
    Given a game state with other card already played
      | player1       | player2        | player3        | player4        |
      | SIX_DIAMONDS  | FOUR_SPADES    | TWO_HEARTS     | THREE_CLUBS    |
      | SIX_CLUBS     | SEVEN_CLUBS    | THREE_DIAMONDS | KING_HEARTS    |
      | QUEEN_SPADES  | FOUR_CLUBS     | EIGHT_CLUBS    | FIVE_HEARTS    |
      | SIX_HEARTS    | EIGHT_SPADES   | NINE_SPADES    | TWO_CLUBS      |
      | TEN_CLUBS     | JACK_CLUBS     | SEVEN_SPADES   | JACK_SPADES    |
      | JACK_DIAMONDS | TWO_SPADES     | KING_CLUBS     | QUEEN_DIAMONDS |
      | SEVEN_HEARTS  | TEN_DIAMONDS   | TEN_HEARTS     | FOUR_HEARTS    |
      | JACK_HEARTS   | QUEEN_HEARTS   | FIVE_CLUBS     | ACE_SPADES     |
      | NINE_HEARTS   | SEVEN_DIAMONDS | ACE_DIAMONDS   | KING_DIAMONDS  |
      | THREE_HEARTS  | KING_SPADES    | ACE_CLUBS      | SIX_SPADES     |
      | ACE_HEARTS    | EIGHT_DIAMONDS | NINE_CLUBS     | NINE_DIAMONDS  |
      | TWO_DIAMONDS  | FOUR_DIAMONDS  | QUEEN_CLUBS    | FIVE_DIAMONDS  |
      | FIVE_SPADES   | EIGHT_HEARTS   | TEN_SPADES     | THREE_SPADES   |
    When I attempt to get a card for a bot
    Then I should be successful in get card for bot
     
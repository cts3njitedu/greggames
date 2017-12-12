Feature: Make sure playing game works properly

  @creatinggame
  Scenario: Testing creating game
    Given a initial game
    When I attempt to create the game
    Then I should be successful in creating the game

  @startinggame
  Scenario: Testing starting game
    Given a created game
    When I attempt to start the game
    Then I should be successful in starting the game

  @makingbid
  Scenario: Testing players bidding
    Given a game and players ready to bid
    When I attempt to bid
    Then I should be successful in bidding

  @playingTrick
  Scenario: Testing trick of game
    Given a game a players ready to play
    When I attempt to play one trick
    Then I should be successful in determining the winner of trick

  @playingHand
  Scenario: Testing one entire hand
    Given a game and players cards
      | PLAYER1       | PLAYER2        | PLAYER3        | PLAYER4        |
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

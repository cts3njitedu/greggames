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
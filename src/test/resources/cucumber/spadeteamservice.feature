Feature: Methods used to manage teams

  Scenario: Determine how much points each team has
    Given a spade game state and bids and scores
      | p1_bid | p1_score | p2_bid | p2_score | p3_bid | p3_score | p4_bid | p4_score | team1_exp | team2_exp |
      |     20 |       20 |     40 |       40 |     50 |      110 |     40 |       60 |       -48 |        82 |
      |      0 |        0 |     40 |       40 |     50 |       60 |     40 |       60 |       151 |        82 |
      |      0 |       10 |     40 |       40 |     50 |       60 |     40 |       60 |       -48 |        82 |
      |     20 |       10 |     40 |       40 |     50 |       60 |     40 |       60 |       -48 |        82 |
      |      0 |       10 |     40 |       40 |     50 |       40 |     40 |       60 |       -48 |        82 |
      |      0 |       10 |     40 |       40 |     50 |       30 |     40 |       60 |       -48 |        82 |
      |      0 |        0 |     40 |       40 |     50 |       30 |     40 |       60 |       -48 |        82 |
      |     20 |       20 |     40 |       40 |     50 |      150 |     40 |       60 |       -48 |        82 |
      |     20 |       20 |     40 |       40 |     50 |       90 |     40 |       60 |       -48 |        82 |
      
    When I try to determine the points for each team
    Then I should get the correct points for each team
@gameWinner
  Scenario: Determine how much points each team has
    Given a spade game state and the final scores for each team
      | 500 | 414 |
      | 505 | 510 |
      | 200 | 100 |
      | 500 | 500 |
      | 149 | 100 |
    When I try to determine the team who won
    Then I should get the correct team

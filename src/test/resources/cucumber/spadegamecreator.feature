Feature: Methods used to manage teams

  Scenario: Determine how much points each team has
    Given a spade game state and bids and scores
      | p1_bid | p1_score | p2_bid | p2_score | p3_bid | p3_score | p4_bid | p4_score | team1_exp | team2_exp |
      |      0 |        0 |     40 |       40 |     50 |       60 |     40 |       60 |       151 |        82 |
      |      0 |       10 |     40 |       40 |     50 |       60 |     40 |       60 |       -48 |        82 |
      |      20 |       10 |     40 |       40 |     50 |       60 |     40 |       60 |       -48 |        82 |
    When I try to determine the points for each team
    Then I should get the correct points for each team

package com.craig.greggames.service.spades.state.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber/spadeteamservice.feature", tags= {"@gameWinner"})
public class SpadeTeamServiceTest {

}

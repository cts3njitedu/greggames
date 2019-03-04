package com.craig.greggames.service.spades.bot;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber/spadebothandler.feature",tags="@determineBotCard")
public class SpadeBotHandlerTest {

}

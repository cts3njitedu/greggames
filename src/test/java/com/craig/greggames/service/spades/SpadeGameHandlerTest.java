package com.craig.greggames.service.spades;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber/spadegamehandler.feature",tags= {"@startGame"},
glue="com.craig.greggames.service.spades")
public class SpadeGameHandlerTest {

	
}

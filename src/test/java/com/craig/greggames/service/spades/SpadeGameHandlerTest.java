package com.craig.greggames.service.spades;

import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber/spadegamehandler.feature",tags= {"@playingTrick"})

public class SpadeGameHandlerTest {

	
}

package com.craig.greggames.spades.validator;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber/genericscenarios.feature")
public class ValidatorFactoryTest {

}

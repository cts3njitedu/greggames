package com.craig.greggames.spades.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.craig.greggames.CardGamesApplication;
import com.craig.greggames.model.game.cards.spades.SpadeGame;
import com.craig.greggames.validator.game.cards.spades.SpadeValidatorEngine;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = CardGamesApplication.class)
public class ValidatorFactorySteps {

	@Autowired
	private SpadeValidatorEngine validatorFactory;
	@Given("^a class$")
	public void a_class() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		validatorFactory.validate(new SpadeGame());
	    throw new PendingException();
	}

	@When("^I call validate method$")
	public void i_call_validate_method() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should have a list$")
	public void i_should_have_a_list() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

}

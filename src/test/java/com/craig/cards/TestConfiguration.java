package com.craig.cards;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

	@Bean
	public PlayerCardGenerators playerCardGenerators() {
		return new PlayerCardGenerators();
	}
}

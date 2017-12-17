package com.craig.greggames.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.craig.greggames.model.game.cards.spades.dao.repo.SpadeGameRepository;

@Configuration
@ComponentScan(basePackages="com.craig.greggames")
public class TestCardApplicationConfig {
	

	@Bean
	public SpadeGameRepository spadeGameRepository() {
		
		
		return Mockito.mock(SpadeGameRepository.class);
	}
	

}

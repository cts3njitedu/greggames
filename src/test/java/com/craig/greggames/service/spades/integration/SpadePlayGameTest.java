package com.craig.greggames.service.spades.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.craig.greggames.CardGamesApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CardGamesApplication.class)
@TestPropertySource(
		  locations = "classpath:application-test.properties")
@SpringBootTest
public class SpadePlayGameTest {

	
	
	@Test
	public void test() {
		System.out.println("Sugar Honey Ice TEa");
	}
}

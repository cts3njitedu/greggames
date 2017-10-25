package com.craig.greggames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class CardGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardGamesApplication.class, args);
	}
}

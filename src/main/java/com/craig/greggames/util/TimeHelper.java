package com.craig.greggames.util;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class TimeHelper {

	public void delayTask(int numberOfSeconds) {
		LocalDateTime future = LocalDateTime.now().plusSeconds(TimeUnit.SECONDS.toSeconds(numberOfSeconds));
		LocalDateTime current = LocalDateTime.now();
		while(current.isBefore(future)) {
			current = LocalDateTime.now();
		}
	}
}

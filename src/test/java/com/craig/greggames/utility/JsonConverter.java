package com.craig.greggames.utility;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

	public  <T> T getSpadeGameState(String fileName, Class<T>clazz) throws URISyntaxException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ClassLoader classLoader = getClass().getClassLoader();
		
		Path path = Paths.get(classLoader.getResource(fileName).toURI());
		
		String jsonString = new String(Files.readAllBytes(path));
		
		return mapper.readValue(jsonString, clazz);
		
		
	}
}

package com.craig.greggames.util;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.craig.greggames.model.spades.SpadeGame;
import com.craig.greggames.model.spades.dao.SpadeGameDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class GregMapper {

	public SpadeGameDAO spadeGameToDAO(SpadeGame spadeGame) {

		String jsonString = convertObjectToString(spadeGame);

		SpadeGameDAO spadeGameDAO = (SpadeGameDAO) convertStringToObject(jsonString, SpadeGameDAO.class);

		return spadeGameDAO;
	}

	public SpadeGame spadeDAOToGame(SpadeGameDAO spadeGameDAO) {

		String jsonString = convertObjectToString(spadeGameDAO);

		SpadeGame spadeGame = (SpadeGame) convertStringToObject(jsonString, SpadeGame.class);

		return spadeGame;
	}

	public <T> String convertObjectToString(T jsonObject) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(jsonObject);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}

	public <T> Object convertStringToObject(String jsonString, Class<?> jsonObject) {
		ObjectMapper mapper = new ObjectMapper();
		Object filledJsonObject = null;
		try {
			filledJsonObject = (Object) mapper.readValue(jsonString, jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filledJsonObject;
	}
}

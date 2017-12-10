package com.craig.greggames.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.craig.greggames.exception.GreggamesException;
import com.craig.greggames.exception.response.GreggamesResponse;

@ControllerAdvice
public class GreggamesResponseExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(value= {GreggamesException.class})
	public ResponseEntity<Object> handleGreggameException(GreggamesException ex){
		
		GreggamesResponse response = new GreggamesResponse("400", ex.getMessage());
		return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
		
	}
	

}

package com.craig.greggames.exception.response;

import org.springframework.web.bind.annotation.ControllerAdvice;


public class GreggamesResponse {

	private String status;
	private String message;

	public GreggamesResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

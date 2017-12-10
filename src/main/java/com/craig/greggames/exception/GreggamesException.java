package com.craig.greggames.exception;

public class GreggamesException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2697677916841119740L;

	private String message;

	public GreggamesException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	

}

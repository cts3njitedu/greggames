package com.craig.greggames.model;

import java.util.HashMap;
import java.util.Map;

public class GreggamesUIResponse {
	
	private boolean error;
	
	private Map<String,Object> message;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Map<String, Object> getMessage() {
		
		if(message==null) {
			
			message = new HashMap<String,Object>();
		}
		return message;
	}

	public void setMessage(Map<String, Object> message) {
		this.message = message;
	}
	
	
	
	
	
	
	

}

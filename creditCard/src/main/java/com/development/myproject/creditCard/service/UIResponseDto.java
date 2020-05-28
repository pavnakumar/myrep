package com.development.myproject.creditCard.service;

import java.util.HashMap;
import java.util.Map;

public class UIResponseDto {
    
	private boolean isSucess;
	private String errorMessage;
	Map<String,Object> response = new HashMap();

    public UIResponseDto(boolean isSucess, String errorMessage) {
	   this.isSucess = isSucess;
	   this.errorMessage = errorMessage;
	
    }

	public boolean isSucess() {
		return isSucess;
	}

	public void setSucess(boolean isSucess) {
		this.isSucess = isSucess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	} 
   
   
	
}

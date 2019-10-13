package com.mujdell2019.hackathon.models.api;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;

public class APIResponse {

	/* Data Members */
	
	private String message;
	private HttpStatus status;
	private JsonNode response;
	
	
	/* Constructors */
	
	public APIResponse() {}
	
	public APIResponse(String message, HttpStatus status, JsonNode response) {
		this.message = message;
		this.status = status;
		this.response = response;
	}
	
	
	/* Getters and Setters */
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }

	public HttpStatus getStatus() { return status; }
	public void setStatus(HttpStatus status) { this.status = status; }

	public JsonNode getResposne() { return response; }
	public void setResposne(JsonNode resposne) { this.response = resposne; }
}

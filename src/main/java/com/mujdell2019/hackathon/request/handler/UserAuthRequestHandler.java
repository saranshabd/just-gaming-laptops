package com.mujdell2019.hackathon.request.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.dao.UserDAO;
import com.mujdell2019.hackathon.models.api.APIResponse;

@Component
@Scope("singleton")
public class UserAuthRequestHandler {

	@Autowired
	private UserDAO userDao;
	
	public APIResponse registerNewUser(String username, String name, String password) {
		
		if (userDao.exists(username))
			// user is already registered with given user-name
			return new APIResponse("user already registered", HttpStatus.BAD_REQUEST, null);
		
		// store user info in DB
		userDao.addUser(username, name, password);
		
		return new APIResponse("user registered", HttpStatus.OK, null);
	}
	
	public APIResponse loginExistingUser(String username, String password) {
		
		if (!userDao.exists(username))
			// user with given user-name does not exists
			return new APIResponse("user not registered", HttpStatus.BAD_REQUEST, null);
		
		if (!userDao.correctPassword(username, password))
			// incorrect password
			return new APIResponse("incorrect password", HttpStatus.BAD_REQUEST, null);
		
		return new APIResponse("user logged in", HttpStatus.OK, null);
	}
	
	public APIResponse signoutExistingUser(String username) {
		
		if (!userDao.exists(username))
			// user with given user-name does not exists
			return new APIResponse("user not registered", HttpStatus.BAD_REQUEST, null);
		
		return new APIResponse("user logged out", HttpStatus.OK, null);
	}
	
	public APIResponse verifyExistingUser(String username) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode response = objectMapper.createObjectNode();
		
		if (!userDao.exists(username)) {
			// user with given user-name does not exists
			((ObjectNode) response).put("isUser", false);
			return new APIResponse("user not registered", HttpStatus.BAD_REQUEST, response);
		}
		
		((ObjectNode) response).put("isUser", true);
		return new APIResponse("user verified", HttpStatus.OK, response);
	}
}

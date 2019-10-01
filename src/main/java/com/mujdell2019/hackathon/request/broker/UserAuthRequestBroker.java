package com.mujdell2019.hackathon.request.broker;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.mujdell2019.hackathon.models.APIResponse;
import com.mujdell2019.hackathon.request.handler.UserAuthRequestHandler;
import com.mujdell2019.hackathon.utils.StringUtils;

@RestController
@RequestMapping("/user/auth")
public class UserAuthRequestBroker {

	@Autowired
	private UserAuthRequestHandler requestHandler;
	
	@Autowired
	private StringUtils stringUtils;
	
	@PostMapping("/register")
	public ResponseEntity<APIResponse> registerNewUser(@RequestBody JsonNode requestBody) {
		
		// get user information from requestBody
		String username = requestBody.get("username").asText();
		String name = requestBody.get("name").asText();
		String password = requestBody.get("password").asText();

		if (stringUtils.containsEmpty(Arrays.asList(username, name, password))) {
			// invalid arguments passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
		
		APIResponse response = requestHandler.registerNewUser(username, name, password);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PutMapping("/login")
	public ResponseEntity<APIResponse> loginExistingUser(@RequestBody JsonNode requestBody) {
		
		// get user information from requestBody
		String username = requestBody.get("username").asText();
		String password = requestBody.get("password").asText();

		if (stringUtils.containsEmpty(Arrays.asList(username, password))) {
			// invalid arguments passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
		
		APIResponse response = requestHandler.loginExistingUser(username, password);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@PutMapping("/signout")
	public ResponseEntity<APIResponse> signoutExistingUser(@RequestBody JsonNode requestBody) {
		
		// get user information from requestBody
		String username = requestBody.get("username").asText();

		if (stringUtils.isEmpty(username)) {
			// invalid arguments passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
		
		APIResponse response = requestHandler.signoutExistingUser(username);
		return new ResponseEntity<>(response, response.getStatus());
	}
}

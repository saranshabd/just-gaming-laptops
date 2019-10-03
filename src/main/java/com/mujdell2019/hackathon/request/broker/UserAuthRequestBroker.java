package com.mujdell2019.hackathon.request.broker;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.mujdell2019.hackathon.models.api.APIResponse;
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
		JsonNode usernameNode = requestBody.get("username");
		JsonNode nameNode = requestBody.get("name");
		JsonNode passwordNode = requestBody.get("password");

		if (null == usernameNode || null == nameNode || null == passwordNode) {
			// invalid arguments passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
		
		String username = usernameNode.asText();
		String name = nameNode.asText();
		String password = passwordNode.asText();
		
		if (stringUtils.containsEmpty(Arrays.asList(username, name, password))) {
			// empty strings passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
		
		APIResponse response = requestHandler.registerNewUser(username, name, password);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@PostMapping("/login")
	public ResponseEntity<APIResponse> loginExistingUser(@RequestBody JsonNode requestBody) {
		
		// get user information from requestBody
		JsonNode usernameNode = requestBody.get("username");
		JsonNode passwordNode = requestBody.get("password");
		
		if (null == usernameNode || null == passwordNode) {
			// invalid arguments passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}

		String username = usernameNode.asText();
		String password = passwordNode.asText();
		
		if (stringUtils.containsEmpty(Arrays.asList(username, password))) {
			// empty strings passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
		
		APIResponse response = requestHandler.loginExistingUser(username, password);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@PostMapping("/signout")
	public ResponseEntity<APIResponse> signoutExistingUser(@RequestBody JsonNode requestBody) {
		
		// get user information from requestBody
		JsonNode usernameNode = requestBody.get("username");
		
		if (null == usernameNode) {
			// invalid arguments passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}

		String username = usernameNode.asText();
		
		if (stringUtils.isEmpty(username)) {
			// empty strings passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
		
		APIResponse response = requestHandler.signoutExistingUser(username);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/verify")
	public ResponseEntity<APIResponse> verifyExistingUser(@RequestBody JsonNode requestBody) {
		
		// get user information from requestBody
		JsonNode usernameNode = requestBody.get("username");
		
		if (null == usernameNode) {
			// invalid arguments passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}

		String username = usernameNode.asText();
		
		if (stringUtils.isEmpty(username)) {
			// empty strings passed
			APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
			return new ResponseEntity<>(response, response.getStatus());
		}
		
		APIResponse response = requestHandler.verifyExistingUser(username);
		return new ResponseEntity<>(response, response.getStatus());
	}
}

package com.mujdell2019.hackathon.request.broker;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.request.handler.UserCartRequestHandler;
import com.mujdell2019.hackathon.utils.ExceptionUtils;
import com.mujdell2019.hackathon.utils.StringUtils;

@RestController
@RequestMapping("/user/cart")
public class UserCartRequestBroker {
	
	@Autowired
	private StringUtils stringUtils;
	@Autowired
	private UserCartRequestHandler requestHandler;
	@Autowired
	private ExceptionUtils exceptionUtils;

	
	@CrossOrigin
	@GetMapping("")
	public ResponseEntity<APIResponse> getAllProductsFromCart(@RequestParam String username) {
		
		try {
			APIResponse response = requestHandler.getAllCartProductFromCart(username);
			return new ResponseEntity<>(response, response.getStatus());
		
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@PutMapping("/item")
	public ResponseEntity<APIResponse> addItemToCart(@RequestBody JsonNode requestBody) {
		
		try {
			// get user information from requestBody
			JsonNode usernameNode = requestBody.get("username");
			JsonNode productIdNode = requestBody.get("productId");

			if (null == usernameNode || null == productIdNode) {
				// invalid arguments passed
				APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(response, response.getStatus());
			}
			
			String username = usernameNode.asText();
			String productId = productIdNode.asText();
			
			if (stringUtils.containsEmpty(Arrays.asList(username, productId))) {
				// empty strings passed
				APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(response, response.getStatus());
			}
			
			APIResponse response = requestHandler.addItemToCart(username, productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@DeleteMapping("/item")
	public ResponseEntity<APIResponse> deleteItemFromCart(@RequestBody JsonNode requestBody) {
		
		try {
			// get user information from requestBody
			JsonNode usernameNode = requestBody.get("username");
			JsonNode productIdNode = requestBody.get("productId");

			if (null == usernameNode || null == productIdNode) {
				// invalid arguments passed
				APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(response, response.getStatus());
			}
			
			String username = usernameNode.asText();
			String productId = productIdNode.asText();
			
			if (stringUtils.containsEmpty(Arrays.asList(username, productId))) {
				// empty strings passed
				APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(response, response.getStatus());
			}
			
			APIResponse response = requestHandler.deleteItemFromCart(username, productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
}

package com.mujdell2019.hackathon.request.broker;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.request.handler.RecommendRequestHandler;
import com.mujdell2019.hackathon.utils.ExceptionUtils;

@RestController
@RequestMapping("/recommend/product")
public class RecommendRequestBroker {

	@Autowired
	private RecommendRequestHandler requestHandler;
	@Autowired
	private ExceptionUtils exceptionUtil;
	

	@CrossOrigin
	@PostMapping("/search")
	public ResponseEntity<APIResponse> searchProducts(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode usernameNode = requestBody.get("username");
			JsonNode queryNode = requestBody.get("query");
			JsonNode filtersNode = requestBody.get("filters");
			
			// check for invalid arguments
			if (null == usernameNode || "".equals(usernameNode.asText()) || null == queryNode || "".equals(queryNode.asText()) || null == filtersNode) {
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			// parse arguments
			String username = usernameNode.asText();
			String query = queryNode.asText();
			
			// convert JsonNode to HashMap
			HashMap<String, String> filters = new ObjectMapper().convertValue(filtersNode, new TypeReference<Map<String, Object>>(){});
			
			APIResponse response = requestHandler.searchProducts(username, query, filters);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtil.reportException(e); }
	}
	
	@CrossOrigin
	@PostMapping("/browsing-history")
	public ResponseEntity<APIResponse> browsingHistory(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode usernameNode = requestBody.get("username");
			
			// check for invalid arguments
			if (null == usernameNode || "".equals(usernameNode.asText())) {
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			// parse arguments
			String username = usernameNode.asText();
			
			APIResponse response = requestHandler.browsingHistoryRecommendation(username);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtil.reportException(e); }
	}
	
	@CrossOrigin
	@PostMapping("/gadgets")
	public ResponseEntity<APIResponse> gadgets(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode usernameNode = requestBody.get("username");
			JsonNode productIdNode = requestBody.get("productId");

			// check for invalid arguments
			if (null == usernameNode || "".equals(usernameNode.asText()) || null == productIdNode || "".equals(productIdNode.asText())) {
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			// parse arguments
			String username = usernameNode.asText();
			String productId = productIdNode.asText();
			
			APIResponse response = requestHandler.gadgetsRecommendation(username, productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtil.reportException(e); }
	}
	
	@CrossOrigin
	@PostMapping("/similar-bought")
	public ResponseEntity<APIResponse> similarBought(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode usernameNode = requestBody.get("username");
			JsonNode productIdNode = requestBody.get("productId");

			// check for invalid arguments
			if (null == usernameNode || "".equals(usernameNode.asText()) || null == productIdNode || "".equals(productIdNode.asText())) {
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			// parse arguments
			String username = usernameNode.asText();
			String productId = productIdNode.asText();
			
			APIResponse response = requestHandler.similarBoughtRecommendation(username, productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtil.reportException(e); }
	}
}

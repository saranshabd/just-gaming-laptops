package com.mujdell2019.hackathon.request.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.request.handler.BackendAnalyticsRequestHandler;
import com.mujdell2019.hackathon.utils.ExceptionUtils;
import com.mujdell2019.hackathon.utils.StringUtils;

@RestController
@RequestMapping("/analytics")
public class BackendAnalyticsRequestBroker {

	@Autowired
	private BackendAnalyticsRequestHandler requestHandler;
	@Autowired
	private StringUtils stringUtils;
	@Autowired
	private ExceptionUtils exceptionUtils;
	
	
	@CrossOrigin
	@PutMapping("/event/click")
	public ResponseEntity<APIResponse> registerClickEvent(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode usernameNode = requestBody.get("username");
			if (null == usernameNode) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			String username = usernameNode.asText();
			if (stringUtils.isEmpty(username)) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			JsonNode productIdNode = requestBody.get("productId");
			if (null == productIdNode) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			String productId = productIdNode.asText();
			if (stringUtils.isEmpty(productId)) {
				// empty strings passed
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			APIResponse response = requestHandler.registerClickEvent(username, productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@PutMapping("/event/cart/add")
	public ResponseEntity<APIResponse> registerAddToCartEvent(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode usernameNode = requestBody.get("username");
			if (null == usernameNode) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			String username = usernameNode.asText();
			if (stringUtils.isEmpty(username)) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			JsonNode productIdNode = requestBody.get("productId");
			if (null == productIdNode) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			String productId = productIdNode.asText();
			if (stringUtils.isEmpty(productId)) {
				// empty strings passed
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			APIResponse response = requestHandler.registerAddToCartEvent(username, productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}

	@CrossOrigin
	@PutMapping("/event/cart/delete")
	public ResponseEntity<APIResponse> registerDeleteFromCartEvent(@RequestBody JsonNode requestBody) {
	
		try {
			JsonNode usernameNode = requestBody.get("username");
			if (null == usernameNode) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			String username = usernameNode.asText();
			if (stringUtils.isEmpty(username)) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			JsonNode productIdNode = requestBody.get("productId");
			if (null == productIdNode) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			String productId = productIdNode.asText();
			if (stringUtils.isEmpty(productId)) {
				// empty strings passed
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			APIResponse response = requestHandler.registerDeleteFromCartEvent(username, productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}

	@CrossOrigin
	@PutMapping("/event/buy")
	public ResponseEntity<APIResponse> registerBuyEvent(@RequestBody JsonNode requestBody) {
	
		try {
			JsonNode usernameNode = requestBody.get("username");
			if (null == usernameNode) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			String username = usernameNode.asText();
			if (stringUtils.isEmpty(username)) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			JsonNode productIdNode = requestBody.get("productId");
			if (null == productIdNode) {
				// invalid arguments passed
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			String productId = productIdNode.asText();
			if (stringUtils.isEmpty(productId)) {
				// empty strings passed
				APIResponse errorResponse = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
			}
			
			APIResponse response = requestHandler.registerBuyEvent(username, productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@GetMapping("/product/top/buy")
	public ResponseEntity<APIResponse> getTopProducts(@RequestParam int count) {
		
		try {
			APIResponse response = requestHandler.topProducts(count);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@GetMapping("/product/worst/buy")
	public ResponseEntity<APIResponse> getWorstProducts(@RequestParam int count) {
		
		try {
			APIResponse response = requestHandler.worstProducts(count);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@GetMapping("/product/top/view")
	public ResponseEntity<APIResponse> getTopViewedProducts(@RequestParam int count) {
		
		try {
			APIResponse response = requestHandler.topViewedProducts(count);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@GetMapping("/order-conversion-rate")
	public ResponseEntity<APIResponse> getOrderConversionRate(@RequestParam String productId) {
		
		try {
			APIResponse response = requestHandler.orderConversionRate(productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
}

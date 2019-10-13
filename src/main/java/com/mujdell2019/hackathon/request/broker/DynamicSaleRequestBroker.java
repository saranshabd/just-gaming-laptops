package com.mujdell2019.hackathon.request.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.request.handler.DynamicSaleRequestHandler;
import com.mujdell2019.hackathon.utils.ExceptionUtils;

@RestController
@RequestMapping("/sale")
public class DynamicSaleRequestBroker {

	@Autowired
	private DynamicSaleRequestHandler requestHandler;
	@Autowired
	private ExceptionUtils exceptionUtils;
	
	@CrossOrigin
	@GetMapping("")
	public ResponseEntity<APIResponse> getSaleFields() {
		
		try {
			APIResponse response = requestHandler.getSaleFields();
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@PutMapping("")
	public ResponseEntity<APIResponse> setSaleFields(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode saleDaysNode = requestBody.get("saleDays");
			JsonNode saleDiscountNode = requestBody.get("saleDiscount");
			
			if (null == saleDaysNode || null == saleDiscountNode) {
				// invalid arguments passed
				APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(response, response.getStatus());
			}
			
			int saleDays = saleDaysNode.asInt();
			double saleDiscount = saleDiscountNode.asDouble();
			
			APIResponse response = requestHandler.setSaleFields(saleDays, saleDiscount);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@CrossOrigin
	@PutMapping("/status")
	public ResponseEntity<APIResponse> setSaleStatus(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode saleStatusNode = requestBody.get("saleStatus");
			
			if (null == saleStatusNode) {
				// invalid arguments passed
				APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(response, response.getStatus());
			}

			boolean saleStatus = saleStatusNode.asBoolean();
			
			APIResponse response = requestHandler.toggleSaleStatus(saleStatus);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@PutMapping("/product")
	public ResponseEntity<APIResponse> addProductInSale(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode productIdNode = requestBody.get("productId");
			if (null == productIdNode || "".equals(productIdNode.asText())) {
				// invalid arguments passed
				APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(response, response.getStatus());
			}
			
			String productId = productIdNode.asText();
			
			APIResponse response = requestHandler.addProductInSale(productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
	
	@DeleteMapping("/product")
	public ResponseEntity<APIResponse> deleteProductFromSale(@RequestBody JsonNode requestBody) {
		
		try {
			JsonNode productIdNode = requestBody.get("productId");
			if (null == productIdNode || "".equals(productIdNode.asText())) {
				// invalid arguments passed
				APIResponse response = new APIResponse("invalid arguments", HttpStatus.BAD_REQUEST, null);
				return new ResponseEntity<>(response, response.getStatus());
			}
			
			String productId = productIdNode.asText();
			
			APIResponse response = requestHandler.deleteProductFromSale(productId);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtils.reportException(e); }
	}
}

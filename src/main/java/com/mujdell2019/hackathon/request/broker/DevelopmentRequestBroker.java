package com.mujdell2019.hackathon.request.broker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.models.db.ProductType;
import com.mujdell2019.hackathon.request.handler.DevelopmentRequestHandler;

/**
 * NOTE
 * ----
 * 
 * THIS REQUEST BROKER IS ONLY USED FOR DEVELOPMENT PURPOSES AND IS NOT SUPPOSED TO BE INCLUDED
 * IN THE PRODUCTION API
 * 
 * COMMENT OUT ALL METHODS BEFORE PUSHING THE CODE
 * 
 * */

@RestController
@RequestMapping("/dev")
public class DevelopmentRequestBroker {
	
	/*@Autowired
	private DevelopmentRequestHandler requestHandler;
	
	@PostMapping("/dell/laptop/multiple")
	public ResponseEntity<APIResponse> addDellMultipleLaptops(@RequestBody JsonNode requestBody) {
		
		try {
			ArrayNode productNodes = (ArrayNode) requestBody.get("products");
			List<DellProductDBModel> dellProducts = new ArrayList<>();
			
			for (JsonNode product : productNodes) {
				DellProductDBModel dellProduct = new DellProductDBModel();
				
				// basic attributes
				dellProduct.setName(product.get("name").asText());
				dellProduct.setImageUrl(product.get("image").asText());
				dellProduct.setPrice(product.get("price").asInt());
				dellProduct.setDiscount(product.get("Disount").asInt());
				
				// product type
				dellProduct.setProductType(ProductType.LAPTOP);
				
				// features
				dellProduct.getFeatures().put("cpu", product.get("cpu").asText());
				dellProduct.getFeatures().put("storage", Integer.toString(product.get("storage").asInt()));
				dellProduct.getFeatures().put("ram_size", Integer.toString(product.get("ram_size").asInt()));
				dellProduct.getFeatures().put("ram_type", product.get("ram_type").asText());
				dellProduct.getFeatures().put("display_fps", Integer.toString(product.get("display_fps").asInt()));
				dellProduct.getFeatures().put("display_size", Integer.toString(product.get("display_type").asInt()));
				dellProduct.getFeatures().put("display_type", product.get("display_type").asText());
				
				dellProducts.add(dellProduct);
			}
			
			APIResponse response = requestHandler.addDellProducts(dellProducts);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			APIResponse errorResponse = new APIResponse("internal server error", HttpStatus.INTERNAL_SERVER_ERROR, null);
			return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
		}
	}*/
}

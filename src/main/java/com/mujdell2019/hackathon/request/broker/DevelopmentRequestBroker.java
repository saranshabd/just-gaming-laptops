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
import com.mujdell2019.hackathon.models.db.product.DellLaptopDBModel;
import com.mujdell2019.hackathon.models.db.product.laptop.LaptopBasicFeaturesDBModel;
import com.mujdell2019.hackathon.models.db.product.laptop.LaptopDisplayFeaturesDBModel;
import com.mujdell2019.hackathon.models.db.product.laptop.LaptopFeaturesDBModel;
import com.mujdell2019.hackathon.models.db.product.laptop.LaptopRamFeaturesDBModel;
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

	@PostMapping("/dell/products")
	public ResponseEntity<APIResponse> addDellProducts(@RequestBody JsonNode requestBody) {
		
		try {
			ArrayNode productNodes = (ArrayNode) requestBody.get("products");
			List<DellProductDBModel> dellProducts = new ArrayList<>();
			
			for (JsonNode product : productNodes) {
				DellProductDBModel dellProduct = new DellProductDBModel();
				
				// basic product features
				dellProduct.setName(product.get("name").asText());
				dellProduct.setImageUrl(product.get("image").asText());
				dellProduct.setPrice(product.get("price").asInt());
				dellProduct.setDiscount(product.get("Disount").asInt());
				
				ProductFeaturesDBModel features = new ProductFeaturesDBModel();
				
				// product basic features
				ProductBasicFeaturesDBModel productBasic = new ProductBasicFeaturesDBModel();
				productBasic.setCpu(product.get("cpu").asText());
				productBasic.setStorage(product.get("storage").asInt());
				
				// product ram features
				ProductRamFeaturesDBModel productRam = new ProductRamFeaturesDBModel();
				productRam.setSize(product.get("ram_size").asInt());
				productRam.setType(product.get("ram_type").asText());
				
				// product display features
				ProductDisplayFeaturesDBModel productDisplay = new ProductDisplayFeaturesDBModel();
				productDisplay.setFps(product.get("display_fps").asInt());
				productDisplay.setSize(product.get("display_size").asInt());
				productDisplay.setType(product.get("display_type").asText());
				
				features.setBasic(productBasic);
				features.setRam(productRam);
				features.setDisplay(productDisplay);
				dellProduct.setFeatures(features);
				
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

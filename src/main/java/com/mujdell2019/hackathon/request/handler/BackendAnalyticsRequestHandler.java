package com.mujdell2019.hackathon.request.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.dao.DellProductDAO;
import com.mujdell2019.hackathon.dao.UserActivityDAO;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;

@Component
@Scope("singleton")
public class BackendAnalyticsRequestHandler {

	@Autowired
	private DellProductDAO dellProductDAO;
	@Autowired
	private UserActivityDAO userActivityDAO;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public APIResponse registerClickEvent(String username, String productId) {
		
		// update dell product clicked event count
		dellProductDAO.updateClickedEventsCount(productId, 1);
		
		// update user activity
		userActivityDAO.updateClickedEventCount(username, productId, 1);

		return new APIResponse("event registered", HttpStatus.OK, null);
	}
	
	public APIResponse registerAddToCartEvent(String username, String productId) {
		
		// update dell product added to cart count
		dellProductDAO.updateAddToCartEventsCount(productId, 1);
		
		// update user activity
		userActivityDAO.updateCartAddedEventCount(username, productId, 1);
		
		return new APIResponse("event registered", HttpStatus.OK, null);
	}
	
	public APIResponse registerDeleteFromCartEvent(String username, String productId) {
		
		// update dell product delete from cart count
		dellProductDAO.updateDeletedFromCartEventsCount(productId, 1);
		
		// update user activity
		userActivityDAO.updateCartDeletedEventCount(username, productId, 1);
		
		return new APIResponse("event registered", HttpStatus.OK, null);
	}
	
	public APIResponse registerBuyEvent(String username, String productId) {
		
		// update dell product buy event count
		dellProductDAO.updateBuyEventsCount(productId, 1);
		
		// update user activity
		userActivityDAO.updateBoughtEventCount(username, productId, 1);
		
		return new APIResponse("event registered", HttpStatus.OK, null);
	}

	public APIResponse topProducts(int count) { // most bought products
		
		// get dell top products from DB
		List<DellProductDBModel> topProducts = dellProductDAO.getTopProducts(count);
		
		// create response object
		JsonNode response = objectMapper.createObjectNode();
		JsonNode dellProducts = objectMapper.createArrayNode();
		for (DellProductDBModel product : topProducts)
			((ArrayNode) dellProducts).add(product.marshal());
		((ObjectNode) response).set("products", dellProducts);
		
		return new APIResponse("top dell products", HttpStatus.OK, response);
	}
	
	public APIResponse worstProducts(int count) { // least bought products
		
		// get dell worst products from DB
		List<DellProductDBModel> worstProducts = dellProductDAO.getWorstProducts(count);
		
		// create response object
		JsonNode response = objectMapper.createObjectNode();
		JsonNode dellProducts = objectMapper.createArrayNode();
		for (DellProductDBModel product : worstProducts)
			((ArrayNode) dellProducts).add(product.marshal());
		((ObjectNode) response).set("products", dellProducts);
		
		return new APIResponse("worst dell products", HttpStatus.OK, response);
	}
	
	public APIResponse topViewedProducts(int count) { // most clicked products
		
		// get most viewed products from DB
		List<DellProductDBModel> mostViewedProducts = dellProductDAO.getTopViewedProducts(count);
		
		// create response object
		JsonNode response = objectMapper.createObjectNode();
		JsonNode dellProducts = objectMapper.createArrayNode();
		for (DellProductDBModel product : mostViewedProducts)
			((ArrayNode) dellProducts).add(product.marshal());
		((ObjectNode) response).set("products", dellProducts);
		
		return new APIResponse("top viewed dell products", HttpStatus.OK, response);
	}
	
	public APIResponse orderConversionRate(String productId) {
		
		// get order conversion rate of a particular product
		double orderConversionRate = dellProductDAO.getOrderConversion(productId);
		
		// create response object
		JsonNode response = objectMapper.createObjectNode();
		((ObjectNode) response).put("productId", productId);
		((ObjectNode) response).put("orderConversionRate", orderConversionRate);
		
		return new APIResponse("order conversion rate of " + productId, HttpStatus.OK, response);
	}
}

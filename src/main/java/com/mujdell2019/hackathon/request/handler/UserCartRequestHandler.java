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
import com.mujdell2019.hackathon.dao.UserDAO;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;

@Component
@Scope("singleton")
public class UserCartRequestHandler {
	
	@Autowired
	private UserDAO userDAO;

	public APIResponse getAllCartProductFromCart(String username) {
		
		// get all items from user cart
		List<DellProductDBModel> cartItems = userDAO.getAllItemFromCart(username);
		
		
		// parse response data into JSON format
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode response = objectMapper.createObjectNode();
		
		JsonNode cartItemsNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : cartItems)
			((ArrayNode) cartItemsNode).add(item.marshal());
		
		((ObjectNode) response).set("cart", cartItemsNode);
		
		
		APIResponse result = new APIResponse("user cart items", HttpStatus.OK, response);
		return result;
	}
	
	public APIResponse addItemToCart(String username, String productId) {
		
		// check if item is already present in cart
		if (!userDAO.checkProductInCart(username, productId)) {
			
			// add item to cart
			userDAO.addToCart(username, productId);
		}

		APIResponse response = new APIResponse("item added to cart", HttpStatus.OK, null);
		return response;
	}
	
	public APIResponse deleteItemFromCart(String username, String productId) {
		
		if (!userDAO.checkProductInCart(username, productId)) {
			
			// item not present in cart
			
			APIResponse response = new APIResponse("item not present in cart", HttpStatus.BAD_REQUEST, null);
			return response;
		}
		
		APIResponse response = new APIResponse("item deleted from cart", HttpStatus.OK, null);
		return response;
	}
}

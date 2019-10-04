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
import com.mujdell2019.hackathon.dao.UserDAO;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.models.db.product.DellLaptopDBModel;

@Component
@Scope("singleton")
public class UserCartRequestHandler {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private DellProductDAO dellProductDAO;

	public APIResponse getAllCartProductFromCart(String username) {
		
		// get all items from user cart
		List<DellLaptopDBModel> cartItems = userDAO.getAllItemFromCart(username);
		
		
		// parse response data into JSON format
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode response = objectMapper.createObjectNode();
		
		JsonNode cartItemsNode = objectMapper.createArrayNode();
		for (DellLaptopDBModel item : cartItems)
			((ArrayNode) cartItemsNode).add(item.marshal());
		
		((ObjectNode) response).set("cart", cartItemsNode);
		
		
		return new APIResponse("user cart items", HttpStatus.OK, response);
	}
	
	public APIResponse addItemToCart(String username, String productId) {
		
		// check if product id is valid
		if (!dellProductDAO.checkProductId(productId))
			return new APIResponse("invalid product id", HttpStatus.BAD_REQUEST, null);
		
		// check if item is already present in cart
		if (!userDAO.checkProductInCart(username, productId)) {
			
			// add item to cart
			userDAO.addToCart(username, productId);
		}

		return new APIResponse("item added to cart", HttpStatus.OK, null);
	}
	
	public APIResponse deleteItemFromCart(String username, String productId) {
		
		// check if product id is valid
		if (!dellProductDAO.checkProductId(productId))
			return new APIResponse("invalid product id", HttpStatus.BAD_REQUEST, null);

		// check if item is not present in cart
		if (!userDAO.checkProductInCart(username, productId))
			return new APIResponse("item not present in cart", HttpStatus.BAD_REQUEST, null);
		
		return new APIResponse("item deleted from cart", HttpStatus.OK, null);
	}
}

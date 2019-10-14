package com.mujdell2019.hackathon.request.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.dao.DellProductDAO;
import com.mujdell2019.hackathon.dao.UserActivityDAO;
import com.mujdell2019.hackathon.dao.UserDAO;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.models.db.UserActivityDBModel;
import com.mujdell2019.hackathon.utils.MLUtil;
import com.mujdell2019.hackathon.utils.QueryUtil;

@Component
@Scope("singleton")
public class RecommendRequestHandler {

	@Autowired
	private QueryUtil queryUtil;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MLUtil mlUtil;
	@Autowired
	private DellProductDAO dellProductDAO;
	@Autowired
	private UserActivityDAO userActivityDAO;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	

	public APIResponse searchProducts(String username, String query, HashMap<String, String> filters) throws IOException {
		
		// check if user with given user-name exists or not
		if (!userDAO.exists(username))
			return new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);

		// parse query string into HashMap
		HashMap<String, String> resultingQuery = queryUtil.parseQuery(query, filters);
		
		// send search query to ML server
		List<String> productIds = mlUtil.searchProduct(resultingQuery);
		
		// get corresponding dell products
		List<DellProductDBModel> result = dellProductDAO.getProducts(productIds);
		
	
		// create response object
		
		ObjectNode response = objectMapper.createObjectNode();
		ArrayNode productsNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : result)
			productsNode.add(item.marshal());
		response.set("products", productsNode);
		
		
		return new APIResponse("searched products", HttpStatus.OK, response);
	}

	public APIResponse browsingHistoryRecommendation(String username) throws IOException {
		
		// check if user with given user-name exists or not
		if (!userDAO.exists(username))
			return new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
		
		// load user activity in DB
		UserActivityDBModel userActivity = userActivityDAO.getUserActivity(username);
		List<String> cartAddedItems = new ArrayList<>(userActivity.getCartAdded().keySet());
		List<String> clickedItems = new ArrayList<>(userActivity.getClicked().keySet());
		
		// get recommended products from ML server
		List<String> productIds = mlUtil.browsingHistoryBasedRecommendation(cartAddedItems, clickedItems);
		
		// get corresponding dell products
		List<DellProductDBModel> products = dellProductDAO.getProducts(productIds);
		
		
		// create response object
		
		ObjectNode response = objectMapper.createObjectNode();
		ArrayNode productsNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : products)
			productsNode.add(item.marshal());
		response.set("products", productsNode);
		
		return new APIResponse("browsing history recommended products", HttpStatus.OK, response);
	}
	
	public APIResponse gadgetsRecommendation(String username, String productId) throws IOException {
		
		// check if user with given user-name exists or not
		if (!userDAO.exists(username))
			return new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
		
		// get recommendation from ML server
		List<String> productIds = mlUtil.gadgetRecommendation(productId);
		
		// load corresponding dell products
		List<DellProductDBModel> products = dellProductDAO.getProducts(productIds);
		
		
		// create response object
		
		ObjectNode response = objectMapper.createObjectNode();
		ArrayNode productsNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : products)
			productsNode.add(item.marshal());
		response.set("products", productsNode);
		
		return new APIResponse("browsing history recommended products", HttpStatus.OK, response);
	}
	
	public APIResponse similarBoughtRecommendation(String username, String productId) throws IOException {
		
		// check if user with given user-name exists or not
		if (!userDAO.exists(username))
			return new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);
		
		// get recommendation from ML server
		List<String> productIds = mlUtil.similarBoughtRecommendation(productId);
		
		// load corresponding dell products
		List<DellProductDBModel> products = dellProductDAO.getProducts(productIds);
		
		
		// create response object
		
		ObjectNode response = objectMapper.createObjectNode();
		ArrayNode productsNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : products)
			productsNode.add(item.marshal());
		response.set("products", productsNode);
		
		return new APIResponse("browsing history recommended products", HttpStatus.OK, response);
	}
}

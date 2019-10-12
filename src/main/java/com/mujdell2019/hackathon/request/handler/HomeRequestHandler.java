package com.mujdell2019.hackathon.request.handler;

import java.io.IOException;
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
import com.mujdell2019.hackathon.dao.UserDAO;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.utils.MLUtil;
import com.mujdell2019.hackathon.utils.QueryUtil;

@Component
@Scope("singleton")
public class HomeRequestHandler {

	@Autowired
	private QueryUtil queryUtil;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MLUtil mlUtil;
	@Autowired
	private DellProductDAO dellProductDAO;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	
	public APIResponse searchProducts(String username, String query) throws IOException {
		
		// check if user with given user-name exists or not
		if (!userDAO.exists(username))
			return new APIResponse("private route", HttpStatus.UNAUTHORIZED, null);

		// parse query string
		HashMap<String, String> filters = new HashMap<>();
		HashMap<String, String> resultingQuery = queryUtil.parseQuery(query, filters);
		
		// get all dell products
		List<DellProductDBModel> dellProducts = dellProductDAO.getAll();
		
		// search query in DB and get product IDs of all the resulting products
		List<String> productIds = mlUtil.searchProduct(resultingQuery, filters, dellProducts);
		
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
}

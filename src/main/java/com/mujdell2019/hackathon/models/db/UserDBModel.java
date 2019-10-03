package com.mujdell2019.hackathon.models.db;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBTable(tableName="users")
public class UserDBModel implements IMarshal {
	
	/* Data Members */

	private String username;
	private String name;
	private String password;
	private List<String> cart; // stores id of all products
	
	
	/* Constructors */
	
	public UserDBModel() { cart = new ArrayList<>(); }
	
	public UserDBModel(String username, String name, String password) {
		this();
		this.username = username;
		this.name = name;
		this.password = password;
	}
	
	public UserDBModel(String username, String name, String password, List<String> cart) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.cart = cart;
	}
	
	public UserDBModel(String username) { this.username = username; }

	
	/* Getters and Setters */

	@DynamoDBHashKey(attributeName = "username")
	public String getUsername() {return username; }
	public void setUsername(String username) {this.username = username; }

	@DynamoDBAttribute(attributeName = "name")
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@DynamoDBAttribute(attributeName = "password")
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	@DynamoDBAttribute(attributeName = "cart")
	public List<String> getCart() { return cart; }
	public void setCart(List<String> cart) { this.cart = cart; }
	
	
	/* JSON Marshal Method */
	
	@Override
	public JsonNode marshal() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.createObjectNode();
		
		((ObjectNode) result).put("username", getUsername());
		((ObjectNode) result).put("name", getName());
		((ObjectNode) result).put("password", getPassword());
		
		JsonNode cart = objectMapper.createArrayNode();
		for (String cartItem : getCart())
			((ArrayNode) cart).add(cartItem);
		((ObjectNode) result).set("cart", cart);
		
		return result;
	}
}

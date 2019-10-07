package com.mujdell2019.hackathon.models.db;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "userActivity")
public class UserActivityDBModel {

	/* Data Members */
	
	private String username;
	private HashMap<String, Integer> clicked;
	private HashMap<String, Integer> cartAdded;
	private HashMap<String, Integer> cartDeleted;
	private HashMap<String, Integer> bought;
	
	
	/* Constructors */
	
	public UserActivityDBModel() {
		clicked = new HashMap<>();
		cartAdded = new HashMap<>();
		cartDeleted = new HashMap<>();
		bought = new HashMap<>();
	}
	
	public UserActivityDBModel(String username) {
		this();
		this.username = username;
	}

	public UserActivityDBModel(String username, HashMap<String, Integer> clicked,
			HashMap<String, Integer> cartAdded, HashMap<String, Integer> cartDeleted,
			HashMap<String, Integer> bought) {
		
		this.username = username;
		this.clicked = clicked;
		this.cartAdded = cartAdded;
		this.cartDeleted = cartDeleted;
		this.bought = bought;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBHashKey(attributeName = "username")
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	@DynamoDBAttribute(attributeName = "clicked")
	public HashMap<String, Integer> getClicked() { return clicked; }
	public void setClicked(HashMap<String, Integer> clicked) { this.clicked = clicked; }

	@DynamoDBAttribute(attributeName = "cartAdded")
	public HashMap<String, Integer> getCartAdded() { return cartAdded; }
	public void setCartAdded(HashMap<String, Integer> cartAdded) { this.cartAdded = cartAdded; }

	@DynamoDBAttribute(attributeName = "cartDeleted")
	public HashMap<String, Integer> getCartDeleted() { return cartDeleted; }
	public void setCartDeleted(HashMap<String, Integer> cartDeleted) { this.cartDeleted = cartDeleted; }

	@DynamoDBAttribute(attributeName = "bought")
	public HashMap<String, Integer> getBought() { return bought; }
	public void setBought(HashMap<String, Integer> bought) { this.bought = bought; }
}

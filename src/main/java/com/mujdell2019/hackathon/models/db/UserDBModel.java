package com.mujdell2019.hackathon.models.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="users")
public class UserDBModel {
	
	/* Data Members */

	private String username;
	private String name;
	private String password;
	
	
	/* Constructors */
	
	public UserDBModel() {}
	
	public UserDBModel(String username, String name, String password) {
		this.username = username;
		this.name = name;
		this.password = password;
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
}

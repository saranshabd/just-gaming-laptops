package com.mujdell2019.hackathon.models.db.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBDocument
public class ProductRamFeaturesDBModel implements IMarshal {
	
	/* Data Members */
	
	private String type;
	private int size;
	
	
	/* Constructors */
	
	public ProductRamFeaturesDBModel() {}
	
	public ProductRamFeaturesDBModel(String type, int size) {
		this.type = type;
		this.size = size;
	}

	
	/* Getters and Setters */
	
	@DynamoDBAttribute(attributeName = "type")
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	@DynamoDBAttribute(attributeName = "size")
	public int getSize() {return size; }
	public void setSize(int size) { this.size = size; }
	
	
	/* JSON Marshal Method */
	
	@Override
	public JsonNode marshal() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.createObjectNode();
		
		((ObjectNode) result).put("type", getType());
		((ObjectNode) result).put("size", getSize());
		
		return result;
	}
}

package com.mujdell2019.hackathon.models.db.product.laptop;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBDocument
public class LaptopBasicFeaturesDBModel implements IMarshal {

	/* Data Members */
	
	private int storage;
	private String cpu;
	
	
	/* Constructors */
	
	public LaptopBasicFeaturesDBModel() {}
	
	public LaptopBasicFeaturesDBModel(int storage, String cpu) {
		this.storage = storage;
		this.cpu = cpu;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBAttribute(attributeName = "storage")
	public int getStorage() { return storage; }
	public void setStorage(int storage) { this.storage = storage; }
	
	@DynamoDBAttribute(attributeName = "cpu")
	public String getCpu() { return cpu; }
	public void setCpu(String cpu) { this.cpu = cpu; }
	
	
	/* JSON Marshal Method */
	
	@Override
	public JsonNode marshal() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.createObjectNode();
		
		((ObjectNode) result).put("storage", getStorage());
		((ObjectNode) result).put("cpu", getCpu());
		
		return result;
	}
}

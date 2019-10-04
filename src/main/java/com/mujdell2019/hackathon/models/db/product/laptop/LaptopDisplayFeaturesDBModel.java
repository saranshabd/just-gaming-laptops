package com.mujdell2019.hackathon.models.db.product.laptop;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBDocument
public class LaptopDisplayFeaturesDBModel implements IMarshal {
	
	/* Data Members */
	
	private String type;
	private int fps;
	private int size;
	
	
	/* Constructors */
	
	public LaptopDisplayFeaturesDBModel() {}
	
	public LaptopDisplayFeaturesDBModel(String type, int fps, int size) {
		this.type = type;
		this.fps = fps;
		this.size = size;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBAttribute(attributeName = "type")
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	@DynamoDBAttribute(attributeName = "fps")
	public int getFps() { return fps; }
	public void setFps(int fps) { this.fps = fps; }

	@DynamoDBAttribute(attributeName = "size")
	public int getSize() { return size; }
	public void setSize(int size) { this.size = size; }
	
	
	/* JSON Marshal Method */
	
	@Override
	public JsonNode marshal() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.createObjectNode();
		
		((ObjectNode) result).put("type", getType());
		((ObjectNode) result).put("fps", getFps());
		((ObjectNode) result).put("size", getSize());
		
		return result;
	}
}

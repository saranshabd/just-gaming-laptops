package com.mujdell2019.hackathon.models.db.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class ProductDisplayFeaturesDBModel {
	
	/* Data Members */
	
	private String type;
	private int fps;
	private int size;
	
	
	/* Constructors */
	
	public ProductDisplayFeaturesDBModel() {}
	
	public ProductDisplayFeaturesDBModel(String type, int fps, int size) {
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
}

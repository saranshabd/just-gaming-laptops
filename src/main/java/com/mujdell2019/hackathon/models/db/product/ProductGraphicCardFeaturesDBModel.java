package com.mujdell2019.hackathon.models.db.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class ProductGraphicCardFeaturesDBModel {
	
	/* Data Members */
	
	private String type;
	private int size;
	
	
	/* Constructors */
	
	public ProductGraphicCardFeaturesDBModel() {}
	
	public ProductGraphicCardFeaturesDBModel(String type, int size) {
		this.type = type;
		this.size = size;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBAttribute(attributeName = "type")
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	@DynamoDBAttribute(attributeName = "size")
	public int getSize() { return size; }
	public void setSize(int size) { this.size = size; }
}

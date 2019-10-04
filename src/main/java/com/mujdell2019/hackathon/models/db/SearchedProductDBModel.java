package com.mujdell2019.hackathon.models.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;
import com.mujdell2019.hackathon.models.UserEvents;
import com.mujdell2019.hackathon.models.db.product.ProductFeaturesDBModel;

@DynamoDBTable(tableName = "searchedProducts")
public class SearchedProductDBModel implements IMarshal {
	
	/* Data Members */
	
	private String userId;
	private int frequency;
	private String lastSearched;
	private int price;
	private UserEvents event; // e.g. item added to cart, item bought, item clicked, etc
	private ProductFeaturesDBModel features;
	
	
	/* Constructors */
	
	public SearchedProductDBModel() {
		features = new ProductFeaturesDBModel();
	}
	
	public SearchedProductDBModel(String userId) {
		this();
		this.userId = userId;
	}

	public SearchedProductDBModel(String userId, int frequency, String lastSearched, int price, UserEvents event, ProductFeaturesDBModel features) {
		this.userId = userId;
		this.frequency = frequency;
		this.lastSearched = lastSearched;
		this.price = price;
		this.event = event;
		this.features = features;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBHashKey(attributeName = "userId")
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	@DynamoDBAttribute(attributeName = "frequency")
	public int getFrequency() { return frequency; }
	public void setFrequency(int frequency) { this.frequency = frequency; }

	@DynamoDBAttribute(attributeName = "lastSearched")
	public String getLastSearched() { return lastSearched; }
	public void setLastSearched(String lastSearched) { this.lastSearched = lastSearched; }

	@DynamoDBAttribute(attributeName = "price")
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }

	@DynamoDBAttribute(attributeName = "event")
	public UserEvents getEvent() { return event; }
	public void setEvent(UserEvents event) { this.event = event; }

	@DynamoDBAttribute(attributeName = "features")
	public ProductFeaturesDBModel getFeatures() { return features; }
	public void setFeatures(ProductFeaturesDBModel features) { this.features = features; }
	
	
	/* JSON Marshal Method */
	
	@Override
	public JsonNode marshal() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.createObjectNode();
		
		((ObjectNode) result).put("userId", userId);
		((ObjectNode) result).put("frequency", getFrequency());
		((ObjectNode) result).put("price", getPrice());
		((ObjectNode) result).put("lastSearched", getLastSearched());
		((ObjectNode) result).put("event", getEvent().name());
		((ObjectNode) result).set("features", getFeatures().marshal());
		
		return result;
	}
}

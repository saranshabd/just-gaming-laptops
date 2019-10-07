package com.mujdell2019.hackathon.models.db;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBTable(tableName = "products")
public class DellProductDBModel implements IMarshal {

	/* Data Members */
	
	// product related fields
	private String productId; // primary key
	private String name;
	private int price;
	private String imageUrl;
	private String productType; // global secondary index key
	private String gpu;
	private HashMap<String, String> features;
	
	// back-end analysis fields
	private int clickedCount;
	private int cartAddedCount;
	private int cartDeletedCount;
	private int boughtCount;
	
	// dynamic sale fields
	private boolean isInSale;
	private double discount;
	private int saleBuyCount;
	
	
	/* Constructors */

	public DellProductDBModel() { features = new HashMap<>(); }
	
	public DellProductDBModel(String productId) {
		this();
		this.productId = productId;
	}
	
	public DellProductDBModel(String productId, String productType) {
		this(productId);
		this.productType = productType;
	}
	
	public DellProductDBModel(String productId, 
			String name, int price, 
			String imageUrl, double discount, String gpu,
			boolean isInSale, int saleBuyCount,
			String productType, HashMap<String, String> features,
			int clickedCount, int cartAddedCount, int cartDeletedCount, int boughtCount) {
		
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
		this.discount = discount;
		this.productType = productType;
		this.gpu = gpu;
		this.features = features;
		this.isInSale = isInSale;
		this.saleBuyCount = saleBuyCount;
		
		this.clickedCount = clickedCount;
		this.cartAddedCount = cartAddedCount;
		this.cartDeletedCount = cartDeletedCount;
		this.boughtCount = boughtCount;
	}

	
	/* Getters and Setters */
	
	@DynamoDBHashKey(attributeName = "productId")
	@DynamoDBAutoGeneratedKey
	public String getProductId() { return productId; }
	public void setProductId(String productId) { this.productId = productId; }
	
	@DynamoDBIndexHashKey(attributeName = "productType", globalSecondaryIndexName = "productType")
	public String getProductType() { return productType; }
	public void setProductType(String productType) { this.productType = productType; }
	
	@DynamoDBAttribute(attributeName = "name")
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@DynamoDBAttribute(attributeName = "price")
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }

	@DynamoDBAttribute(attributeName = "imageUrl")
	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

	@DynamoDBAttribute(attributeName = "discount")
	public double getDiscount() { return discount; }
	public void setDiscount(double discount) { this.discount = discount; }
	
	@DynamoDBAttribute(attributeName = "gpu")
	public String getGpu() { return gpu; }
	public void setGpu(String gpu) { this.gpu = gpu; }

	@DynamoDBAttribute(attributeName = "features")
	public HashMap<String, String> getFeatures() { return features; }
	public void setFeatures(HashMap<String, String> features) { this.features = features; }
	
	@DynamoDBAttribute(attributeName = "clickedCount")
	public int getClickedCount() { return clickedCount; }
	public void setClickedCount(int clickedCount) { this.clickedCount = clickedCount; }

	@DynamoDBAttribute(attributeName = "cartAddedCount")
	public int getCartAddedCount() { return cartAddedCount; }
	public void setCartAddedCount(int cartAddedCount) { this.cartAddedCount = cartAddedCount; }

	@DynamoDBAttribute(attributeName = "cartDeletedCount")
	public int getCartDeletedCount() { return cartDeletedCount; }
	public void setCartDeletedCount(int cartDeletedCount) { this.cartDeletedCount = cartDeletedCount; }

	@DynamoDBAttribute(attributeName = "boughtCount")
	public int getBoughtCount() { return boughtCount; }
	public void setBoughtCount(int boughtCount) { this.boughtCount = boughtCount; }
	
	@DynamoDBAttribute(attributeName = "isInSale")
	public boolean isInSale() { return isInSale; }
	public void setInSale(boolean isInSale) { this.isInSale = isInSale; }

	@DynamoDBAttribute(attributeName = "saleBuyCount")
	public int getSaleBuyCount() { return saleBuyCount; }
	public void setSaleBuyCount(int saleBuyCount) { this.saleBuyCount = saleBuyCount; }
	
	
	/* JSON Marshal Method */

	@Override
	public JsonNode marshal() {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.createObjectNode();
		
		// basic attributes
		((ObjectNode) result).put("productId", getProductId());
		((ObjectNode) result).put("name", getName());
		((ObjectNode) result).put("price", getPrice());
		((ObjectNode) result).put("imageUrl", getImageUrl());
		((ObjectNode) result).put("discount", getDiscount());
		((ObjectNode) result).put("productType", getProductType());
		((ObjectNode) result).put("isInSale", isInSale());
		((ObjectNode) result).put("saleBuyCount", getSaleBuyCount());
		
		// features
		JsonNode featuresNode = objectMapper.createObjectNode();
		for (String key : getFeatures().keySet())
			((ObjectNode) featuresNode).put(key, getFeatures().get(key));
		((ObjectNode) result).set("features", featuresNode);
		
		// back-end analysis fields
		((ObjectNode) result).put("clickedCount", getClickedCount());
		((ObjectNode) result).put("cartAddedCount", getCartAddedCount());
		((ObjectNode) result).put("cartDeletedCount", getCartDeletedCount());
		((ObjectNode) result).put("boughtCount", getBoughtCount());
		
		return result;
	}
}

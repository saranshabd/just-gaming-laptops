package com.mujdell2019.hackathon.models.db;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.mujdell2019.hackathon.models.db.product.ProductFeaturesDBModel;

@DynamoDBTable(tableName = "dellProducts")
public class DellProductDBModel {

	/* Data Members */
	
	private String id;
	private String name;
	private int price;
	private List<String> locations;
	private double discount;
	private ProductFeaturesDBModel features;
	
	
	/* Constructors */
	
	public DellProductDBModel() {
		locations = new ArrayList<>();
		features = new ProductFeaturesDBModel();
	}
	
	public DellProductDBModel(String id) {
		this();
		this.id = id;
	}
	
	public DellProductDBModel(String id, String name, int price, List<String> locations, double discount, ProductFeaturesDBModel features) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.locations = locations;
		this.discount = discount;
		this.features = features;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	@DynamoDBAttribute(attributeName = "name")
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	@DynamoDBAttribute(attributeName = "price")
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }

	@DynamoDBAttribute(attributeName = "locations")
	public List<String> getLocations() { return locations; }
	public void setLocations(List<String> locations) { this.locations = locations; }

	@DynamoDBAttribute(attributeName = "discount")
	public double getDiscount() { return discount; }
	public void setDiscount(double discount) { this.discount = discount; }

	@DynamoDBAttribute(attributeName = "features")
	public ProductFeaturesDBModel getFeatures() { return features; }
	public void setFeatures(ProductFeaturesDBModel features) { this.features = features; }
}
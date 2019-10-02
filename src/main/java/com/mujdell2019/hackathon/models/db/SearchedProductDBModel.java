package com.mujdell2019.hackathon.models.db;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.mujdell2019.hackathon.models.db.product.ProductFeaturesDBModel;

@DynamoDBTable(tableName = "searchedProducts")
public class SearchedProductDBModel {
	
	/* Data Members */
	
	// input
	private String searchQuery;
	
	// outputs
	private int frequency;
	private String lastSearched;
	private int price;
	private List<String> locations;
	private String event; // (search/view/add-cart/delete-cart)
	private ProductFeaturesDBModel features;
	
	
	/* Constructors */
	
	public SearchedProductDBModel() {
		locations = new ArrayList<>();
		features = new ProductFeaturesDBModel();
	}

	public SearchedProductDBModel(String searchQuery, int frequency, String lastSearched, int price, List<String> locations, String event, ProductFeaturesDBModel features) {
		this.searchQuery = searchQuery;
		this.frequency = frequency;
		this.lastSearched = lastSearched;
		this.price = price;
		this.locations = locations;
		this.event = event;
		this.features = features;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBAttribute(attributeName = "searchQuery")
	public String getSearchQuery() { return searchQuery; }
	public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

	@DynamoDBAttribute(attributeName = "frequency")
	public int getFrequency() { return frequency; }
	public void setFrequency(int frequency) { this.frequency = frequency; }

	@DynamoDBAttribute(attributeName = "lastSearched")
	public String getLastSearched() { return lastSearched; }
	public void setLastSearched(String lastSearched) { this.lastSearched = lastSearched; }

	@DynamoDBAttribute(attributeName = "price")
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }

	@DynamoDBAttribute(attributeName = "locations")
	public List<String> getLocations() { return locations; }
	public void setLocations(List<String> locations) { this.locations = locations; }

	@DynamoDBAttribute(attributeName = "event")
	public String getEvent() { return event; }
	public void setEvent(String event) { this.event = event; }

	@DynamoDBAttribute(attributeName = "features")
	public ProductFeaturesDBModel getFeatures() { return features; }
	public void setFeatures(ProductFeaturesDBModel features) { this.features = features; }
}

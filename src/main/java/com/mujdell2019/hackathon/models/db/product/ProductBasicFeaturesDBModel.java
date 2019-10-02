package com.mujdell2019.hackathon.models.db.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class ProductBasicFeaturesDBModel {

	/* Data Members */
	
	private int storage;
	private String cpu;
	private String audioType;
	private int battery;
	
	
	/* Constructors */
	
	public ProductBasicFeaturesDBModel() {}
	
	public ProductBasicFeaturesDBModel(int storage, String cpu, String audioType, int battery) {
		this.storage = storage;
		this.cpu = cpu;
		this.audioType = audioType;
		this.battery = battery;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBAttribute(attributeName = "storage")
	public int getStorage() { return storage; }
	public void setStorage(int storage) { this.storage = storage; }
	
	@DynamoDBAttribute(attributeName = "cpu")
	public String getCpu() { return cpu; }
	public void setCpu(String cpu) { this.cpu = cpu; }

	@DynamoDBAttribute(attributeName = "audioType")
	public String getAudioType() { return audioType; }
	public void setAudioType(String audioType) { this.audioType = audioType; }

	@DynamoDBAttribute(attributeName = "battery")
	public int getBattery() { return battery; }
	public void setBattery(int battery) { this.battery = battery; }
}

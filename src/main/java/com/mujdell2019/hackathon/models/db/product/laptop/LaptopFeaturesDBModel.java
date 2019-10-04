package com.mujdell2019.hackathon.models.db.product.laptop;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBDocument
public class LaptopFeaturesDBModel implements IMarshal {

	/* Data Members */

	private LaptopBasicFeaturesDBModel basic;
	private LaptopRamFeaturesDBModel ram;
	private LaptopDisplayFeaturesDBModel display;
	
	
	/* Constructors */
	
	public LaptopFeaturesDBModel() {
		basic = new LaptopBasicFeaturesDBModel();
		ram = new LaptopRamFeaturesDBModel();
		display = new LaptopDisplayFeaturesDBModel();
	}
	
	public LaptopFeaturesDBModel(LaptopBasicFeaturesDBModel basic, LaptopRamFeaturesDBModel ram, LaptopDisplayFeaturesDBModel display) {
		this.basic = basic;
		this.ram = ram;
		this.display = display;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBAttribute(attributeName = "basic")
	public LaptopBasicFeaturesDBModel getBasic() { return basic; }
	public void setBasic(LaptopBasicFeaturesDBModel basic) { this.basic = basic; }

	@DynamoDBAttribute(attributeName = "ram")
	public LaptopRamFeaturesDBModel getRam() { return ram; }
	public void setRam(LaptopRamFeaturesDBModel ram) { this.ram = ram; }

	@DynamoDBAttribute(attributeName = "display")
	public LaptopDisplayFeaturesDBModel getDisplay() { return display; }
	public void setDisplay(LaptopDisplayFeaturesDBModel display) { this.display = display; }
	
	
	/* JSON Marshal Method */
	
	@Override
	public JsonNode marshal() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.createObjectNode();
		
		((ObjectNode) result).set("basic", getBasic().marshal());
		((ObjectNode) result).set("ram", getRam().marshal());
		((ObjectNode) result).set("display", getDisplay().marshal());
		
		return result;
	}
}

package com.mujdell2019.hackathon.models.db.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBDocument
public class ProductFeaturesDBModel implements IMarshal {

	/* Data Members */

	private ProductBasicFeaturesDBModel basic;
	private ProductRamFeaturesDBModel ram;
	private ProductDisplayFeaturesDBModel display;
	
	
	/* Constructors */
	
	public ProductFeaturesDBModel() {
		basic = new ProductBasicFeaturesDBModel();
		ram = new ProductRamFeaturesDBModel();
		display = new ProductDisplayFeaturesDBModel();
	}
	
	public ProductFeaturesDBModel(ProductBasicFeaturesDBModel basic, ProductRamFeaturesDBModel ram, ProductDisplayFeaturesDBModel display) {
		this.basic = basic;
		this.ram = ram;
		this.display = display;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBAttribute(attributeName = "basic")
	public ProductBasicFeaturesDBModel getBasic() { return basic; }
	public void setBasic(ProductBasicFeaturesDBModel basic) { this.basic = basic; }

	@DynamoDBAttribute(attributeName = "ram")
	public ProductRamFeaturesDBModel getRam() { return ram; }
	public void setRam(ProductRamFeaturesDBModel ram) { this.ram = ram; }

	@DynamoDBAttribute(attributeName = "display")
	public ProductDisplayFeaturesDBModel getDisplay() { return display; }
	public void setDisplay(ProductDisplayFeaturesDBModel display) { this.display = display; }
	
	
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

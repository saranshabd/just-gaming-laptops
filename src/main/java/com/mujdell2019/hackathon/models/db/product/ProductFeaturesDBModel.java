package com.mujdell2019.hackathon.models.db.product;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class ProductFeaturesDBModel {

	/* Data Members */

	private ProductBasicFeaturesDBModel basic;
	private ProductRamFeaturesDBModel ram;
	private ProductDisplayFeaturesDBModel display;
	private ProductGraphicCardFeaturesDBModel graphicCard;
	
	
	/* Constructors */
	
	public ProductFeaturesDBModel() {
		basic = new ProductBasicFeaturesDBModel();
		ram = new ProductRamFeaturesDBModel();
		display = new ProductDisplayFeaturesDBModel();
		graphicCard = new ProductGraphicCardFeaturesDBModel();
	}
	
	public ProductFeaturesDBModel(ProductBasicFeaturesDBModel basic, ProductRamFeaturesDBModel ram, ProductDisplayFeaturesDBModel display, ProductGraphicCardFeaturesDBModel graphicCard) {
		this.basic = basic;
		this.ram = ram;
		this.display = display;
		this.graphicCard = graphicCard;
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

	@DynamoDBAttribute(attributeName = "graphicCard")
	public ProductGraphicCardFeaturesDBModel getGraphicCard() { return graphicCard; }
	public void setGraphicCard(ProductGraphicCardFeaturesDBModel graphicCard) { this.graphicCard = graphicCard; }
}

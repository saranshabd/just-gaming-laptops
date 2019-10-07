package com.mujdell2019.hackathon.models.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBTable(tableName = "dynamicSale")
public class DynamicSaleDBModel implements IMarshal {

	/* Data Members */
	
	// sale fields
	private boolean isSale;
	private double saleDiscount;
	private int saleDays;
	
	// default sale fields
	private double defaultSaleDiscount;
	private int defaultSaleDays;
	
	
	/* Constructors */
	
	public DynamicSaleDBModel() {}
	
	public DynamicSaleDBModel(boolean isSale, double saleDiscount, int saleDays) {
		this.isSale = isSale;
		this.saleDiscount = saleDiscount;
		this.saleDays = saleDays;
	}
	
	
	/* Getters and Setters */
	
	@DynamoDBHashKey(attributeName = "saleDays")
	public int getSaleDays() { return saleDays; }
	public void setSaleDays(int saleDays) { this.saleDays = saleDays; }
	
	@DynamoDBAttribute(attributeName = "isSale")
	public boolean isSale() { return isSale; }
	public void setSale(boolean isSale) { this.isSale = isSale; }

	@DynamoDBAttribute(attributeName = "saleDiscount")
	public double getSaleDiscount() { return saleDiscount; }
	public void setSaleDiscount(double saleDiscount) { this.saleDiscount = saleDiscount; }

	@DynamoDBHashKey(attributeName = "defaultSaleDiscount")
	public double getDefaultSaleDiscount() { return defaultSaleDiscount; }
	public void setDefaultSaleDiscount(double defaultSaleDiscount) { this.defaultSaleDiscount = defaultSaleDiscount; }

	@DynamoDBHashKey(attributeName = "defaultSaleDays")
	public int getDefaultSaleDays() { return defaultSaleDays; }
	public void setDefaultSaleDays(int defaultSaleDays) { this.defaultSaleDays = defaultSaleDays; }
	
	
	/* JSON Marshal Method */
	
	public JsonNode marshal() {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode result = objectMapper.createObjectNode();
		
		((ObjectNode) result).put("isSale", isSale());
		((ObjectNode) result).put("saleDiscount", getSaleDiscount());
		((ObjectNode) result).put("saleDays", getSaleDays());
		
		return result;
	}
}

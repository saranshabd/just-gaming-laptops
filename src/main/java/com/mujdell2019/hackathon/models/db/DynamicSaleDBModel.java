package com.mujdell2019.hackathon.models.db;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.IMarshal;

@DynamoDBTable(tableName = "dynamicSale")
public class DynamicSaleDBModel implements IMarshal {

	/* Data Members */
	
	// sale fields
	private boolean isSale;
	private double saleDiscount;
	private int saleDays;
	private List<DellProductDBModel> saleProducts;
	
	// default sale fields
	private double defaultSaleDiscount;
	private int defaultSaleDays;
	
	
	/* Constructors */
	
	public DynamicSaleDBModel() { saleProducts = new ArrayList<>(); }
	
	public DynamicSaleDBModel(boolean isSale, double saleDiscount, int saleDays, List<DellProductDBModel> saleProducts) {
		this.isSale = isSale;
		this.saleDiscount = saleDiscount;
		this.saleDays = saleDays;
		this.saleProducts = saleProducts;
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

	@DynamoDBAttribute(attributeName = "defaultSaleDiscount")
	public double getDefaultSaleDiscount() { return defaultSaleDiscount; }
	public void setDefaultSaleDiscount(double defaultSaleDiscount) { this.defaultSaleDiscount = defaultSaleDiscount; }

	@DynamoDBAttribute(attributeName = "defaultSaleDays")
	public int getDefaultSaleDays() { return defaultSaleDays; }
	public void setDefaultSaleDays(int defaultSaleDays) { this.defaultSaleDays = defaultSaleDays; }
	
	@DynamoDBAttribute(attributeName = "saleProducts")
	public List<DellProductDBModel> getSaleProducts() { return saleProducts; }
	public void setSaleProducts(List<DellProductDBModel> saleProducts) { this.saleProducts = saleProducts; }
	
	
	/* JSON Marshal Method */

	public ObjectNode marshal() {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode result = objectMapper.createObjectNode();
		
		// sale fields
		result.put("isSale", isSale());
		result.put("saleDiscount", getSaleDiscount());
		result.put("saleDays", getSaleDays());
		
		// default sale fields
		result.put("defaultSaleDiscount", getDefaultSaleDiscount());
		result.put("defaultSaleDays", getDefaultSaleDays());
		
		// sale products
		ArrayNode saleProducts = objectMapper.createArrayNode();
		for (DellProductDBModel product : getSaleProducts())
			saleProducts.add(product.marshal());
		result.set("saleProducts", saleProducts);
		
		return result;
	}
}

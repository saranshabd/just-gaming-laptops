package com.mujdell2019.hackathon.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mujdell2019.hackathon.models.db.product.DellLaptopDBModel;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;

@Component
@Scope("singleton")
public class DellProductDAO {

	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	
	
	/**
	 * DEVELOPMENT METHOD: NOT TO BE INCLUDED IN PRODUCTION API
	 * */
	/*public void addProduct(List<DellProductDBModel> products) {

		dynamoDBUtil.getDynamoDBMapper().batchSave(products);
	}*/
	
	/**
	 * check if given product id is valid
	 * */
	public boolean checkProductId(String id) {
		
		// search for product with given product id
		DellLaptopDBModel product = new DellLaptopDBModel(id);
		DynamoDBQueryExpression<DellLaptopDBModel> queryExpression = new DynamoDBQueryExpression<DellLaptopDBModel>().withHashKeyValues(product);

		return 0 != dynamoDBUtil.getDynamoDBMapper().count(DellLaptopDBModel.class, queryExpression);
	}

	/**
	 * load single dell product by its product id
	 * */
	public DellLaptopDBModel getProduct(String id) {

		return dynamoDBUtil.getDynamoDBMapper().load(DellLaptopDBModel.class, id);
	}
	
	/**
	 * load multiple dell products by their respective product id
	 * */
	public List<DellLaptopDBModel> getProducts(List<String> ids) {
	
		// load products from DB
		
		List<DellLaptopDBModel> products = new ArrayList<>(ids.size());
		for (int i = 0; i < ids.size(); ++i)
			products.add(i, new DellLaptopDBModel(ids.get(i)));
	
		Map<String, List<Object>> dbResponse = dynamoDBUtil.getDynamoDBMapper().batchLoad(products);
		
		
		// parse DB response
	
		for (String tableName : dbResponse.keySet())
	
			// since items are only loaded from dell products table,
			// there is only one key in DB response object
			
			for (int i = 0; i < dbResponse.get(tableName).size(); ++i)
				products.add(i, (DellLaptopDBModel) dbResponse.get(tableName).get(i));
		
		return products;
	}
	
	/**
	 * load all dell products in DB
	 * */
	public List<DellLaptopDBModel> getAll() {

		return dynamoDBUtil.getDynamoDBMapper().scan(DellLaptopDBModel.class, new DynamoDBScanExpression());
	}
}

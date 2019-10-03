package com.mujdell2019.hackathon.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;

@Component
@Scope("singleton")
public class DellProductDAO {

	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	
	
	/**
	 * load single dell product by its product id
	 * */
	public DellProductDBModel getProduct(String id) {

		return dynamoDBUtil.getDynamoDBMapper().load(DellProductDBModel.class, id);
	}
	
	/**
	 * load multiple dell products by their respective product id
	 * */
	public List<DellProductDBModel> getProducts(List<String> ids) {
	
		// load products from DB
		
		List<DellProductDBModel> products = new ArrayList<>(ids.size());
		for (int i = 0; i < ids.size(); ++i)
			products.add(i, new DellProductDBModel(ids.get(i)));
	
		Map<String, List<Object>> dbResponse = dynamoDBUtil.getDynamoDBMapper().batchLoad(products);
		
		
		// parse DB response
	
		for (String tableName : dbResponse.keySet())
	
			// since items are only loaded from dell products table,
			// there is only one key in DB response object
			
			for (int i = 0; i < dbResponse.get(tableName).size(); ++i)
				products.add(i, (DellProductDBModel) dbResponse.get(tableName).get(i));
		
		return products;
	}
	
	/**
	 * load all dell products in DB
	 * */
	public List<DellProductDBModel> getAll() {

		return dynamoDBUtil.getDynamoDBMapper().scan(DellProductDBModel.class, new DynamoDBScanExpression());
	}
}

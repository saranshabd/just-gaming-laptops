package com.mujdell2019.hackathon.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mujdell2019.hackathon.models.db.product.SearchedLaptopDBModel;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;

@Component
@Scope("singleton")
public class SearchedProductDAO {

	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	
	
	/**
	 * load single searched product from DB based on searched query
	 * */
	public SearchedLaptopDBModel getProduct(String searchQuery) {
		
		return dynamoDBUtil.getDynamoDBMapper().load(SearchedLaptopDBModel.class, searchQuery);
	}
	
	/**
	 * load multiple searched products from DB based on their respective searched queries
	 * */
	public List<SearchedLaptopDBModel> getProducts(List<String> searchQueries) {
		
		// load products from DB
		Map<String, List<Object>> dbResponse = dynamoDBUtil.getDynamoDBMapper().batchLoad(searchQueries);
		
		
		// parse DB response
		
		List<SearchedLaptopDBModel> result = new ArrayList<>();
	
		for (String tableName : dbResponse.keySet())
	
			// since items are only loaded from searched products table,
			// there is only one key in DB response object
			
			for (Object curr : dbResponse.get(tableName))
				result.add((SearchedLaptopDBModel) curr);
		
		return result;
	}
	
	/**
	 * load all searched products from DB
	 * */
	public List<SearchedLaptopDBModel> getAll() {
		
		return dynamoDBUtil.getDynamoDBMapper().scan(SearchedLaptopDBModel.class, new DynamoDBScanExpression());
	}
}

package com.mujdell2019.hackathon.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mujdell2019.hackathon.models.db.SearchedProductDBModel;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;

@Component
@Scope("singleton")
public class SearchedProductDAO {

	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	
	
	/**
	 * DEVELOPMENT METHOD: NOT TO BE INCLUDED IN PRODUCTION API
	 * */
	/*public void addProducts(List<SearchedProductDBModel> products) {
		
		dynamoDBUtil.getDynamoDBMapper().batchSave(products);
	}*/
	
	
	/**
	 * load single searched product from DB based on searched query
	 * */
	public SearchedProductDBModel getProduct(String searchQuery) {
		
		return dynamoDBUtil.getDynamoDBMapper().load(SearchedProductDBModel.class, searchQuery);
	}
	
	/**
	 * load multiple searched products from DB based on their respective searched queries
	 * */
	public List<SearchedProductDBModel> getProducts(List<String> searchQueries) {
		
		// load products from DB
		Map<String, List<Object>> dbResponse = dynamoDBUtil.getDynamoDBMapper().batchLoad(searchQueries);
		
		
		// parse DB response
		
		List<SearchedProductDBModel> result = new ArrayList<>();
	
		for (String tableName : dbResponse.keySet())
	
			// since items are only loaded from searched products table,
			// there is only one key in DB response object
			
			for (Object curr : dbResponse.get(tableName))
				result.add((SearchedProductDBModel) curr);
		
		return result;
	}
	
	/**
	 * load all searched products from DB
	 * */
	public List<SearchedProductDBModel> getAll() {
		
		return dynamoDBUtil.getDynamoDBMapper().scan(SearchedProductDBModel.class, new DynamoDBScanExpression());
	}
}

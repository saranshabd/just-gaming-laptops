package com.mujdell2019.hackathon.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;

@Component
@Scope("singleton")
public class DellProductDAO {

	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	
	
	/**
	 * DEVELOPMENT METHOD: NOT TO BE INCLUDED IN PRODUCTION API
	 * */
	/*public void addProduct(DellProductDBModel product) {
		
		dynamoDBUtil.getDynamoDBMapper().save(product);
	}*/
	
	/**
	 * DEVELOPMENT METHOD: NOT TO BE INCLUDED IN PRODUCTION API
	 * */
	/*public void addProducts(List<DellProductDBModel> products) {
		
		dynamoDBUtil.getDynamoDBMapper().batchSave(products);
	}*/
	
	
	/**
	 * check if given product id is valid
	 * */
	public boolean checkProductId(String id) {
		
		// search for product with given product id
		DellProductDBModel product = new DellProductDBModel(id);
		DynamoDBQueryExpression<DellProductDBModel> queryExpression = new DynamoDBQueryExpression<DellProductDBModel>().withHashKeyValues(product);

		return 0 != dynamoDBUtil.getDynamoDBMapper().count(DellProductDBModel.class, queryExpression);
	}

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
		
		List<DellProductDBModel> products = new ArrayList<>();
		for (String id : ids)
			products.add(new DellProductDBModel(id));
	
		Map<String, List<Object>> dbResponse = dynamoDBUtil.getDynamoDBMapper().batchLoad(products);
		
		
		// parse DB response
	
		List<DellProductDBModel> result = new ArrayList<>();
		for (String tableName : dbResponse.keySet())
	
			// since items are only loaded from dell products table,
			// there is only one key in DB response object
			
			for (Object item : dbResponse.get(tableName))
				result.add((DellProductDBModel) item);
		
		return result;
	}
	
	/**
	 * load all dell products in DB
	 * */
	public ArrayList<DellProductDBModel> getAll() {

		// load all dell products from DB
		List<DellProductDBModel> results = 
				dynamoDBUtil.getDynamoDBMapper().scan(DellProductDBModel.class, new DynamoDBScanExpression());
		
		// store the results in an ArrayList
		ArrayList<DellProductDBModel> arrResults = new ArrayList<>();
		for (DellProductDBModel result : results)
			arrResults.add(result);
		
		return arrResults;
	}
	
	/**
	 * update clicked events count of a product by n
	 * */
	public void updateClickedEventsCount(String productId, int count) {
		
		// load dell product from DB
		DellProductDBModel dellProduct = dynamoDBUtil.getDynamoDBMapper().load(DellProductDBModel.class, productId);
		
		// update product clicked count
		dellProduct.setClickedCount(dellProduct.getClickedCount() + count);
		
		// upload the updated product
		dynamoDBUtil.getDynamoDBMapper().save(dellProduct);
	}
	
	/**
	 * update add to cart events count of a product by n
	 * */
	public void updateAddToCartEventsCount(String productId, int count) {
		
		// load dell product from DB
		DellProductDBModel dellProduct = dynamoDBUtil.getDynamoDBMapper().load(DellProductDBModel.class, productId);
		
		// update product added to cart count
		dellProduct.setCartAddedCount(dellProduct.getCartAddedCount() + count);
		
		// upload the updated product
		dynamoDBUtil.getDynamoDBMapper().save(dellProduct);
	}
	
	/**
	 * update delete from cart events count of a product by n
	 * */
	public void updateDeletedFromCartEventsCount(String productId, int count) {
		
		// load dell product from DB
		DellProductDBModel dellProduct = dynamoDBUtil.getDynamoDBMapper().load(DellProductDBModel.class, productId);
		
		// update product delete from cart count
		dellProduct.setCartDeletedCount(dellProduct.getCartDeletedCount() + count);
		
		// upload the updated product
		dynamoDBUtil.getDynamoDBMapper().save(dellProduct);
	}
	
	/**
	 * update buy events count of a product by n
	 * */
	public void updateBuyEventsCount(String productId, int count) {
		
		// load dell product from DB
		DellProductDBModel dellProduct = dynamoDBUtil.getDynamoDBMapper().load(DellProductDBModel.class, productId);
		
		// update product bought count
		dellProduct.setBoughtCount(dellProduct.getBoughtCount() + count);
		
		// upload the updated product
		dynamoDBUtil.getDynamoDBMapper().save(dellProduct);
	}
	
	/**
	 * get products with most bought events registered
	 * */
	public List<DellProductDBModel> getTopProducts(int count) {
		
		// load all dell products from DB
		ArrayList<DellProductDBModel> products = getAll();
		
		// sort products based on total bought events count (descending order)
		products.sort((DellProductDBModel p1, DellProductDBModel p2) -> p2.getBoughtCount() - p1.getBoughtCount());
		
		// create a list of top 'count' products
		List<DellProductDBModel> result = new LinkedList<>();
		for (int i = 0; i < products.size() && i < count; ++i)
			result.add(products.get(i));
		
		return result;
	}
	
	/**
	 * get products with least bought events registered
	 * <br>
	 * */
	public List<DellProductDBModel> getWorstProducts(int count) {
		
		// load all dell products from DB
		ArrayList<DellProductDBModel> products = getAll();
		
		// sort products based on total bought events count (ascending order)
		products.sort((DellProductDBModel p1, DellProductDBModel p2) -> p1.getBoughtCount() - p2.getBoughtCount());
		
		// create a list of top 'count' products
		List<DellProductDBModel> result = new LinkedList<>();
		for (int i = 0; i < products.size() && i < count; ++i)
			result.add(products.get(i));
		
		return result;
	}
	
	/**
	 * get products with most view event counts
	 * */
	public List<DellProductDBModel> getTopViewedProducts(int count) {
		
		// load all dell products from DB
		ArrayList<DellProductDBModel> products = getAll();
		
		// sort products based on total bought events count (descending order)
		products.sort((DellProductDBModel p1, DellProductDBModel p2) -> p2.getClickedCount() - p1.getClickedCount());
		
		// create a list of top 'count' products
		List<DellProductDBModel> result = new LinkedList<>();
		for (int i = 0; i < products.size() && i < count; ++i)
			result.add(products.get(i));
		
		return result;
	}
	
	/**
	 * get order conversion rate of a particular product
	 * */
	public double getOrderConversion(String productId) {
		
		// get dell product from DB
		DellProductDBModel product = getProduct(productId);
		
		// calculate order conversion rate
		double orderConversionRate = product.getBoughtCount() / product.getCartAddedCount();
		
		return orderConversionRate;
	}
	
	/**
	 * update all Dell products in DB
	 * */
	public void updateProducts(List<DellProductDBModel> products) {

		dynamoDBUtil.getDynamoDBMapper().batchSave(products);
	}
}

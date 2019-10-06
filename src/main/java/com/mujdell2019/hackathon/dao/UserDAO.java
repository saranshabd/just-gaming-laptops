package com.mujdell2019.hackathon.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.models.db.UserDBModel;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;
import com.mujdell2019.hackathon.utils.EncryptionUtils;

@Component
@Scope("singleton")
public class UserDAO {

	@Autowired
	private EncryptionUtils encryptionUtil;
	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	@Autowired
	private DellProductDAO dellProductDAO;
	
	// configuration object for consistent reads from DB
	private DynamoDBMapperConfig consistentReadConfig = DynamoDBMapperConfig.builder()
			.withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT).build();
	
	/**
	 * check in DB whether user already exists with given user-name
	 * */
	public boolean exists(String username) {
		
		// search for user with given user-name in DB
		UserDBModel user = new UserDBModel(username);
		DynamoDBQueryExpression<UserDBModel> queryExpression = new DynamoDBQueryExpression<UserDBModel>()
																	.withConsistentRead(true)
																	.withHashKeyValues(user);

		return 0 != dynamoDBUtil.getDynamoDBMapper().count(UserDBModel.class, queryExpression);
	}
	
	/**
	 * create new user in DB
	 * */
	public void addUser(String username, String name, String password) {
		
		// hash password
		String hashPassword = encryptionUtil.hash(password);
		
		// store user in DB
		UserDBModel user = new UserDBModel(username, name, hashPassword);
		dynamoDBUtil.getDynamoDBMapper().save(user);
	}
	
	/**
	 * check whether password corresponding to given user-name matches the provided one or not
	 * */
	public boolean correctPassword(String username, String password) {
		
		// load user info from DB
		UserDBModel user = dynamoDBUtil.getDynamoDBMapper().load(UserDBModel.class, username, consistentReadConfig);
		
		// compare passwords
		return encryptionUtil.compareHash(user.getPassword(), password);
	}
	
	/**
	 * add product to cart
	 * */
	public void addToCart(String username, String productId) {
		
		// load user information from DB
		UserDBModel user = dynamoDBUtil.getDynamoDBMapper().load(UserDBModel.class, username, consistentReadConfig);
		
		// add given product id to user cart
		user.getCart().add(productId);
		
		// update user information in DB
		dynamoDBUtil.getDynamoDBMapper().save(user);
	}
	
	/**
	 * delete product from cart
	 * */
	public void deleteFromCart(String username, String productId) {
		
		// load user information from DB
		UserDBModel user = dynamoDBUtil.getDynamoDBMapper().load(UserDBModel.class, username, consistentReadConfig);
		
		// delete given product id from user cart
		user.getCart().remove(user.getCart().indexOf(productId));
		
		// update user information in DB
		dynamoDBUtil.getDynamoDBMapper().save(user);
	}
	
	/**
	 * check if item is already present in cart
	 * */
	public boolean checkProductInCart(String username, String productId) {
		
		// load user profile from DB
		UserDBModel user = dynamoDBUtil.getDynamoDBMapper().load(UserDBModel.class, username, consistentReadConfig);
		
		// check if given product id is present in user cart
		for (String product : user.getCart())
			if (product.equals(productId)) return true;
		
		return false;
	}
	
	/**
	 * get product id of all items from user cart
	 * */
	private List<String> getAllItemProductIdsFromCart(String username) {
		
		// load user profile from DB
		UserDBModel user = dynamoDBUtil.getDynamoDBMapper().load(UserDBModel.class, username, consistentReadConfig);

		return user.getCart();
	}
	
	/**
	 * get all products from user cart
	 * */
	public List<DellProductDBModel> getAllItemFromCart(String username) {
		
		// load product id of all items in user cart
		List<String> itemsProductId = getAllItemProductIdsFromCart(username);
		
		// load product information of all the product ids
		List<DellProductDBModel> result = dellProductDAO.getProducts(itemsProductId);
		
		return result;
	}
}

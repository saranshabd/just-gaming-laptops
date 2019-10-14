package com.mujdell2019.hackathon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.mujdell2019.hackathon.models.db.UserActivityDBModel;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;

@Component
@Scope("singleton")
public class UserActivityDAO {

	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	
	// configuration object for consistent reads from DB
	private DynamoDBMapperConfig consistentReadConfig = DynamoDBMapperConfig.builder()
			.withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT).build();

	
	public void updateClickedEventCount(String username, String productId, int count) {
		// load user activity from DB
		UserActivityDBModel userActivity = getUserActivity(username);
		
		// update user activity
		if (userActivity.getClicked().containsKey(productId))
			userActivity.getClicked().put(productId, 1 + userActivity.getClicked().get(productId));
		else userActivity.getClicked().put(productId, 1);
		
		// save updated user activity in DB
		dynamoDBUtil.getDynamoDBMapper().save(userActivity);
	}
	
	public void updateCartAddedEventCount(String username, String productId, int count) {
		// load user activity from DB
		UserActivityDBModel userActivity = getUserActivity(username);
		
		// update user activity
		if (userActivity.getCartAdded().containsKey(productId))
			userActivity.getCartAdded().put(productId, 1 + userActivity.getCartAdded().get(productId));
		else userActivity.getCartAdded().put(productId, 1);
		
		// save updated user activity in DB
		dynamoDBUtil.getDynamoDBMapper().save(userActivity);
	}

	public void updateCartDeletedEventCount(String username, String productId, int count) {
		// load user activity from DB
		UserActivityDBModel userActivity = getUserActivity(username);
		
		// update user activity
		if (userActivity.getCartDeleted().containsKey(productId))
			userActivity.getCartDeleted().put(productId, 1 + userActivity.getCartDeleted().get(productId));
		else userActivity.getCartDeleted().put(productId, 1);
		
		// save updated user activity in DB
		dynamoDBUtil.getDynamoDBMapper().save(userActivity);
	}

	public void updateBoughtEventCount(String username, String productId, int count) {
		// load user activity from DB
		UserActivityDBModel userActivity = getUserActivity(username);
		
		// update user activity
		if (userActivity.getBought().containsKey(productId))
			userActivity.getBought().put(productId, 1 + userActivity.getBought().get(productId));
		else userActivity.getBought().put(productId, 1);
		
		// save updated user activity in DB
		dynamoDBUtil.getDynamoDBMapper().save(userActivity);
	}
	
	public UserActivityDBModel getUserActivity(String username) {
		
		return dynamoDBUtil.getDynamoDBMapper().load(UserActivityDBModel.class, username, consistentReadConfig);
	}
}

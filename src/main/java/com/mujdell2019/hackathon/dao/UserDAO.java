package com.mujdell2019.hackathon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
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
	
	/*
	 * check in DB whether user already exists with given user-name
	 * */
	public boolean exists(String username) {
		
		// search for user with given user-name in DB
		UserDBModel user = new UserDBModel(username);
		DynamoDBQueryExpression<UserDBModel> queryExpression = new DynamoDBQueryExpression<UserDBModel>().withHashKeyValues(user);

		return 0 != dynamoDBUtil.getDynamoDBMapper().count(UserDBModel.class, queryExpression);
	}
	
	/*
	 * create new user in DB
	 * */
	public void addUser(String username, String name, String password) {
		
		// hash password
		String hashPassword = encryptionUtil.hash(password);
		
		// store user in DB
		UserDBModel user = new UserDBModel(username, name, hashPassword);
		dynamoDBUtil.getDynamoDBMapper().save(user);
	}
	
	/*
	 * check whether password corresponding to given user-name matches the provided one or not
	 * */
	public boolean correctPassword(String username, String password) {
		
		// load user info from DB
		UserDBModel user = dynamoDBUtil.getDynamoDBMapper().load(UserDBModel.class, username);
		
		// compare passwords
		return encryptionUtil.compareHash(user.getPassword(), password);
	}
}

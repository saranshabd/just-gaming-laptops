package com.mujdell2019.hackathon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.mujdell2019.hackathon.models.UserDBModel;
import com.mujdell2019.hackathon.utils.EncryptionUtils;

@Component
@Scope("singleton")
public class UserDAO {

	@Autowired
	private EncryptionUtils encryptionUtil;
	
	// Amazon DynamoDB objects
	private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).withRegion(Regions.AP_SOUTH_1).build();
	private DynamoDBMapper mapper = new DynamoDBMapper(client);

	/*
	 * check in DB whether user already exists with given user-name
	 * */
	public boolean exists(String username) {
		
		// search for user with given user-name in DB
		UserDBModel user = new UserDBModel(username);
		DynamoDBQueryExpression<UserDBModel> queryExpression = new DynamoDBQueryExpression<UserDBModel>().withHashKeyValues(user);

		return 0 != mapper.count(UserDBModel.class, queryExpression);
	}
	
	/*
	 * create new user in DB
	 * */
	public void addUser(String username, String name, String password) {
		
		// hash password
		String hashPassword = encryptionUtil.hash(password);
		
		// store user in DB
		UserDBModel user = new UserDBModel(username, name, hashPassword);
		mapper.save(user);
	}
	
	/*
	 * check whether password corresponding to given user-name matches the provided one or not
	 * */
	public boolean correctPassword(String username, String password) {
		
		// load user info from DB
		UserDBModel user = mapper.load(UserDBModel.class, username);
		
		// compare passwords
		return encryptionUtil.compareHash(user.getPassword(), password);
	}
}

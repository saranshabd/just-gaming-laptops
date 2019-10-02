package com.mujdell2019.hackathon.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Component
@Scope("singleton")
public class DynamoDBUtil {

	// AWS credentials
	private DefaultAWSCredentialsProviderChain credentials = new DefaultAWSCredentialsProviderChain();
	// AWS region
	private Regions region = Regions.AP_SOUTH_1;
	// DynamoDB client object
	private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(region).withCredentials(credentials).build();
	// DynamoDB mapper
	private DynamoDBMapper mapper = new DynamoDBMapper(client);
	
	
	/*
	 * get DynamoDB mapper
	 * */
	public DynamoDBMapper getDynamoDBMapper() { return mapper; }
}

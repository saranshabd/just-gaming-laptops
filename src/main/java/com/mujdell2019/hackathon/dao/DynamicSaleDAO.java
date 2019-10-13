package com.mujdell2019.hackathon.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mujdell2019.hackathon.models.db.DynamicSaleDBModel;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;

@Component
@Scope("singleton")
public class DynamicSaleDAO {

	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	
	public DynamicSaleDBModel getSaleFields() {
		// load sale fields from DB
		List<DynamicSaleDBModel> saleFields = 
				dynamoDBUtil.getDynamoDBMapper().scan(DynamicSaleDBModel.class, new DynamoDBScanExpression());
		// since there is only one record in DB
		return saleFields.get(0);
	}
	
	public void resetSaleFields() {
		// get dynamic sale object from DB
		DynamicSaleDBModel saleFields = getSaleFields();
		
		// reset values
		saleFields.setSale(true);
		saleFields.setSaleDays(saleFields.getDefaultSaleDays());
		saleFields.setSaleDiscount(saleFields.getDefaultSaleDiscount());
		
		// update object in DB
		dynamoDBUtil.getDynamoDBMapper().save(saleFields);
	}

	public void setSaleFields(DynamicSaleDBModel saleFields) {
		
		dynamoDBUtil.getDynamoDBMapper().save(saleFields);
	}
	
	public void setSaleFields(int saleDays, double saleDiscount) {
		// get dynamic sale object from DB
		DynamicSaleDBModel saleFields = getSaleFields();
		
		// set values
		saleFields.setSaleDays(saleDays);
		saleFields.setSaleDiscount(saleDiscount);
		
		// update object in DB
		dynamoDBUtil.getDynamoDBMapper().save(saleFields);
	}
	
	public void toggleIsSale(boolean isSale) {
		// get dynamic sale object from DB
		DynamicSaleDBModel saleFields = getSaleFields();
		
		// set values
		saleFields.setSale(isSale);
		
		// update object in DB
		dynamoDBUtil.getDynamoDBMapper().save(saleFields);
	}
}

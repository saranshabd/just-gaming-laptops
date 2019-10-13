package com.mujdell2019.hackathon.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.models.db.DynamicSaleDBModel;
import com.mujdell2019.hackathon.utils.DynamicSaleUtil;
import com.mujdell2019.hackathon.utils.DynamoDBUtil;

@Component
@Scope("singleton")
public class DynamicSaleDAO {

	@Autowired
	private DynamoDBUtil dynamoDBUtil;
	@Autowired
	private DynamicSaleUtil dynamicSaleUtil;
	
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
		setSaleFields(saleFields);
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
		setSaleFields(saleFields);
	}
	
	public void setSaleDiscount(double saleDiscount) {
		// get sale fields from DB
		DynamicSaleDBModel saleFields = getSaleFields();
		
		// set sale discount
		saleFields.setSaleDiscount(saleDiscount);
		
		// update sale fields in DB
		setSaleFields(saleFields);
	}
	
	public void toggleIsSale(boolean isSale) {
		// get dynamic sale object from DB
		DynamicSaleDBModel saleFields = getSaleFields();
		
		// set values
		saleFields.setSale(isSale);
		
		// update object in DB
		setSaleFields(saleFields);
	}
	
	public void updateSaleDiscount() {
		// get sale fields from DB
		DynamicSaleDBModel saleFields = getSaleFields();
		
		// get all products up for sale
		List<DellProductDBModel> saleProducts = saleFields.getSaleProducts();
		
		// calculate and set sale discount
		double saleDiscount = dynamicSaleUtil.calculateDiscount(saleProducts);
		saleFields.setSaleDiscount(saleDiscount);
		
		// update sale fields in DB
		setSaleFields(saleFields);
	}

	public void addProductInSale(DellProductDBModel product) {
		// get dynamic sale fields from DB
		DynamicSaleDBModel saleFields = getSaleFields();
		
		// add product up for sale
		saleFields.getSaleProducts().add(product);
		
		// update sale fields in DB
		setSaleFields(saleFields);
		
		// update sale discount
		updateSaleDiscount();
	}

	public void deleteProductFromSale(String productId) {
		// get dynamic sale fields from DB
		DynamicSaleDBModel saleFields = getSaleFields();
		
		// delete product from sale
		int index = 0, requiredIndex = 0;
		for (DellProductDBModel product : saleFields.getSaleProducts()) {
			if (productId.equals(product.getProductId())) {
				requiredIndex = index;
			}
			++index;
		}
		saleFields.getSaleProducts().remove(requiredIndex);
		
		// update sale fields in DB
		setSaleFields(saleFields);
		
		// update sale discount
		updateSaleDiscount();
	}
}

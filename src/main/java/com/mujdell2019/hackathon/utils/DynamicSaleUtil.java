package com.mujdell2019.hackathon.utils;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mujdell2019.hackathon.models.db.DellProductDBModel;

@Component
@Scope("singleton")
public class DynamicSaleUtil {

	public double calculateDiscount(List<DellProductDBModel> saleProducts) {
		
		int[] allDiscounts = new int[saleProducts.size()];
		
		int i = 0;
		for (DellProductDBModel product : saleProducts) {

			// calculate order conversion rate for current product
			int buyCount = product.getBoughtCount();
			int addToCartCount = product.getCartAddedCount();
			double rate = (buyCount / addToCartCount) * 100;
			
			// calculate corresponding discount
			int discount;
			if (rate >= 0 && rate < 10) {
				discount = 20;
			} else if (rate >= 10 && rate < 20) {
				discount = 15;
			} else if (rate >= 20 && rate < 40) {
				discount = 10;
			} else {
				discount = 5;
			}
			
			allDiscounts[i++] = discount;
		}
		
		
		// calculate mean discount
		double mean = 0;
		for (int curr : allDiscounts) mean += curr;
		mean /= saleProducts.size();
		
		return mean;
	}
}

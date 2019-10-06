package com.mujdell2019.hackathon.workers;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.mujdell2019.hackathon.dao.DellProductDAO;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;

/**
 * clears analytics fields of all Dell products at the start of a month
 * */

public class ClearDellProductsAnalyticsFieldsWorderThread extends Thread {

	@Autowired
	private DellProductDAO dellProductDAO;
	
	@Override
	public void run() {
		
		while (true) {
			
			// get current date
			int currDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			
			if (1 == currDate) { // today's the first of month
				
				// get all Dell products from DB
				List<DellProductDBModel> products = dellProductDAO.getAll();
				
				// clear analytics fields of all the products
				for (DellProductDBModel product : products) {
					product.setClickedCount(0);
					product.setCartAddedCount(0);
					product.setCartDeletedCount(0);
					product.setBoughtCount(0);
				}
				
				// update all the Dell products in DB
				dellProductDAO.updateProducts(products);
				
				// wait for next month
				try { Thread.sleep(TimeUnit.DAYS.toMillis(28)); }
				catch (InterruptedException e) {}
				
				continue;
			}
			
			// wait for next day
			try { Thread.sleep(TimeUnit.DAYS.toMillis(1)); }
			catch (InterruptedException e) {}
		}
	}
}

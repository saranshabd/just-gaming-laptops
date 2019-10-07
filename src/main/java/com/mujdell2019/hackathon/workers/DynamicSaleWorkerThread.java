package com.mujdell2019.hackathon.workers;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.mujdell2019.hackathon.dao.DellProductDAO;
import com.mujdell2019.hackathon.dao.DynamicSaleDAO;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.models.db.DynamicSaleDBModel;

public class DynamicSaleWorkerThread extends Thread {

	@Autowired
	private DellProductDAO dellProductDAO;
	@Autowired
	private DynamicSaleDAO dynamicSaleDAO;
	
	@Override
	public void run() {
		
		while (true) {
			
			// get current date
			int currDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			
			if (15 == currDate) {  // today's mid of the month

				// get sale details from DB
				DynamicSaleDBModel saleFields = dynamicSaleDAO.getSaleFields();
				
				long waitTimePeriod = saleFields.isSale() ? 28 - saleFields.getSaleDays() : 28;
				
				if (saleFields.isSale()) {
					
					// get worst 3 Dell products of current month
					List<DellProductDBModel> worstProducts = dellProductDAO.getWorstProducts(3);
					
					// start sale
					for (DellProductDBModel product : worstProducts) {
						product.setInSale(true);
						product.setDiscount(saleFields.getSaleDiscount());
					}
					dellProductDAO.updateProducts(worstProducts);
					
					// wait for sale to close
					try { Thread.sleep(TimeUnit.DAYS.toMillis(saleFields.getSaleDays())); }
					catch (InterruptedException e) {}
					
					
					// close sale
					for (DellProductDBModel product : worstProducts) {
						product.setInSale(false);
						product.setDiscount(0);
					}
					dellProductDAO.updateProducts(worstProducts);
					
				}
				
				// reset sale fields
				dynamicSaleDAO.resetSaleFields();
				
				// wait for next month
				try { Thread.sleep(TimeUnit.DAYS.toMillis(waitTimePeriod)); }
				catch (InterruptedException e) {}
				
				continue;
			}
			
			// wait for next day
			try { Thread.sleep(TimeUnit.DAYS.toMillis(1)); }
			catch (InterruptedException e) {}
		}
	}
}

package com.mujdell2019.hackathon.workers;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.mujdell2019.hackathon.dao.DynamicSaleDAO;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.models.db.DynamicSaleDBModel;

public class DynamicSaleWorkerThread extends Thread {
	
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
					
					// get all dell products up for sale
					List<DellProductDBModel> worstProducts = saleFields.getSaleProducts();
					
					// start sale
					for (DellProductDBModel product : worstProducts) {
						product.setInSale(true);
						product.setDiscount(saleFields.getSaleDiscount());
					}
					dynamicSaleDAO.setSaleFields(saleFields);
					
					// wait for sale to close
					try { Thread.sleep(TimeUnit.DAYS.toMillis(saleFields.getSaleDays())); }
					catch (InterruptedException e) {}
					
					
					// close sale
					for (DellProductDBModel product : worstProducts) {
						product.setInSale(false);
						product.setDiscount(0);
					}
					dynamicSaleDAO.setSaleFields(saleFields);
				}
				
				// reset all sale fields to their default values
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

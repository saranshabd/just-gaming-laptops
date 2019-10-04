package com.mujdell2019.hackathon.request.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.mujdell2019.hackathon.dao.DellProductDAO;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;

/**
 * NOTE
 * ----
 * 
 * THIS REQUEST HANDLER IS ONLY USED FOR DEVELOPMENT PURPOSES AND IS NOT SUPPOSED TO BE INCLUDED
 * IN THE PRODUCTION API
 * 
 * COMMENT OUT ALL METHODS BEFORE PUSHING THE CODE
 * 
 * */

@Component
@Scope("singleton")
public class DevelopmentRequestHandler {
	
	/*@Autowired
	private DellProductDAO dellProductDao;

	public APIResponse addDellProducts(List<DellProductDBModel> products) {
		
		// add dell products in DB
		dellProductDao.addProduct(products);

		return new APIResponse("products added", HttpStatus.OK, null);
	}*/
}

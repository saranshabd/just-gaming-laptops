package com.mujdell2019.hackathon.request.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.dao.DynamicSaleDAO;
import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.models.db.DynamicSaleDBModel;

@Component
@Scope("singleton")
public class DynamicSaleRequestHandler {

	@Autowired
	private DynamicSaleDAO dynamicSaleDAO;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public APIResponse getSaleFields() {
		// get sale fields from DB
		DynamicSaleDBModel saleFields = dynamicSaleDAO.getSaleFields();
		
		// create response object
		JsonNode response = objectMapper.createObjectNode();
		JsonNode saleFieldsNode = saleFields.marshal();
		((ObjectNode) response).set("saleFields", saleFieldsNode);
		
		return new APIResponse("sale fields", HttpStatus.OK, response);
	}
	
	public APIResponse setSaleFields(int saleDays, double saleDiscount) {
		// set sale fields in DB
		dynamicSaleDAO.setSaleFields(saleDays, saleDiscount);
		
		return new APIResponse("sale fields set", HttpStatus.OK, null);
	}
	
	public APIResponse toggleSaleStatus(boolean isSale) {
		// toggle sale status in DB
		dynamicSaleDAO.toggleIsSale(isSale);
		
		return new APIResponse("sale status set", HttpStatus.OK, null);
	}
}

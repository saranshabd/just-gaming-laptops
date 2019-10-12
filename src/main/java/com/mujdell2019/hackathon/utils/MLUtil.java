package com.mujdell2019.hackathon.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mujdell2019.hackathon.models.db.DellProductDBModel;
import com.mujdell2019.hackathon.models.db.SearchedProductDBModel;

@Component
@Scope("singleton")
public class MLUtil {
	
	@Value("${application.ml.base-url}")
	private String mlApiUrl;

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();
	

	public List<String> searchProduct(HashMap<String, String> query, HashMap<String, String> filters, 
			List<DellProductDBModel> dellProducts) throws IOException {

		String url = mlApiUrl + "/recommendation/content-based";
		
		
		// create request object
		
		JsonNode requestBody = objectMapper.createObjectNode();
		JsonNode queryNode = objectMapper.createObjectNode();
		JsonNode filtersNode = objectMapper.createObjectNode();
		
		for (String key : query.keySet())
			((ObjectNode) queryNode).put(key, query.get(key));
		
		for (String key : filters.keySet())
			((ObjectNode) filtersNode).put(key, filters.get(key));
		
		((ObjectNode) requestBody).set("query", queryNode);
		((ObjectNode) requestBody).set("filters", filtersNode);
		
		
		// make HTTP request
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
		String responseAsStr = restTemplate.postForObject(url, request, String.class);
		JsonNode response = objectMapper.readTree(responseAsStr);
		
		
		// retrieve product IDs from response object
		
		ArrayNode productIdsNode = (ArrayNode) response.get("productIds");
		List<String> productIds = new ArrayList<>();
		for (JsonNode item : productIdsNode)
			productIds.add(item.asText());
		
		
		return productIds;
	}
	
	public List<String> browsingHistoryBasedRecommendation(List<String> cartAddedItems,  List<String> clickedItems, 
			List<SearchedProductDBModel> anonymousData, List<DellProductDBModel> headphones, 
			List<DellProductDBModel> backpack,  List<DellProductDBModel> keyboard, List<DellProductDBModel> monitor,
			List<DellProductDBModel> mouse) throws IOException {
		
		String url = mlApiUrl + "/recommendation/browsing-history";
		
		
		// create request object
		
		// total items = cart added items + clicked items
		List<String> totalUserItems = cartAddedItems;
		totalUserItems.addAll(clickedItems);
		
		// anonymous data
		ArrayNode anonymousDataNode = objectMapper.createArrayNode();
		for (SearchedProductDBModel item : anonymousData)
			anonymousDataNode.add(item.marshal());
		
		// headphones
		ArrayNode headphonesNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : headphones)
			headphonesNode.add(item.marshal());
		
		// backpack
		ArrayNode backpackNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : backpack)
			backpackNode.add(item.marshal());
		
		// keyboard
		ArrayNode keyboardNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : keyboard)
			keyboardNode.add(item.marshal());
		
		// monitor
		ArrayNode monitorNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : monitor)
			monitorNode.add(item.marshal());
		
		// mouse
		ArrayNode mouseNode = objectMapper.createArrayNode();
		for (DellProductDBModel item : mouse)
			mouseNode.add(item.marshal());
		
		
		JsonNode requestBody = objectMapper.createObjectNode();
		((ObjectNode) requestBody).set("searchedProducts", anonymousDataNode);
		((ObjectNode) requestBody).set("heaphones", headphonesNode);
		((ObjectNode) requestBody).set("backpack", backpackNode);
		((ObjectNode) requestBody).set("keyboard", keyboardNode);
		((ObjectNode) requestBody).set("monitor", monitorNode);
		((ObjectNode) requestBody).set("mouse", mouseNode);
		
		
		// make HTTP request
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
		String responseAsStr = restTemplate.postForObject(url, request, String.class);
		JsonNode response = objectMapper.readTree(responseAsStr);
		
		
		// retrieve product IDs from response object
		
		ArrayNode productIdsNode = (ArrayNode) response.get("productIds");
		List<String> productIds = new ArrayList<>();
		for (JsonNode item : productIdsNode)
			productIds.add(item.asText());

		return productIds;
	}
}

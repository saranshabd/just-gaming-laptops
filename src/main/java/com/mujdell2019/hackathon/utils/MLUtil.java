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

@Component
@Scope("singleton")
public class MLUtil {
	
	@Value("${application.ml.base-url}")
	private String mlApiUrl;

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();
	

	public List<String> searchProduct(HashMap<String, String> query) throws IOException {

		String url = mlApiUrl + "/content-based";
		
		// create request object
		
		ObjectNode requestBody = objectMapper.createObjectNode();
		ObjectNode queryNode = objectMapper.createObjectNode();
		for (String key : query.keySet())
			queryNode.put(key, query.get(key));
		requestBody.set("query", queryNode);
		
		
		// make HTTP request
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
		String responseAsStr = restTemplate.postForObject(url, request, String.class);
		JsonNode response = objectMapper.readTree(responseAsStr);
		
		
		// retrieve product IDs from response object
		
		ArrayNode productIdsNode = (ArrayNode) response.get("response").get("productIds");
		List<String> productIds = new ArrayList<>();
		
		if (null != productIdsNode) {
			for (JsonNode item : productIdsNode)
				productIds.add(item.asText());
		}
		
		return productIds;
	}

	public List<String> trendingRecommendation() throws IOException {
		
		String url = mlApiUrl + "/trending";
		
		
		// make HTTP request
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<>(null, headers);
		String responseAsStr = restTemplate.postForObject(url, request, String.class);
		JsonNode response = objectMapper.readTree(responseAsStr);
		
		
		// retrieve product IDs from response object
		
		ArrayNode productIdsNode = (ArrayNode) response.get("response").get("productIds");
		List<String> productIds = new ArrayList<>();
		
		if (null != productIdsNode) {
			for (JsonNode item : productIdsNode)
				productIds.add(item.asText());
		}

		return productIds;
	}
	
	/**
	 * returns both gadgets and laptops
	 * */
	public List<String> browsingHistoryBasedRecommendation(List<String> cartAddedItems, List<String> clickedItems) throws IOException {
		
		String url = mlApiUrl + "/browsing-history";		
		
		// create request object		

		ObjectNode requestBody = objectMapper.createObjectNode();
		ArrayNode products = objectMapper.createArrayNode();
		for (String item : cartAddedItems)
			products.add(item);
		for (String item : clickedItems)
			products.add(item);
		requestBody.set("cart_added", products);
		
		
		// make HTTP request
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
		String responseAsStr = restTemplate.postForObject(url, request, String.class);
		JsonNode response = objectMapper.readTree(responseAsStr);
		
		
		// retrieve product IDs from response object
		
		ArrayNode productIdsNode = (ArrayNode) response.get("response").get("productIds");
		List<String> productIds = new ArrayList<>();
		
		if (null != productIdsNode) {
			for (JsonNode item : productIdsNode)
				productIds.add(item.asText());
		}

		return productIds;
	}
	
	public List<String> gadgetRecommendation(String productId) throws IOException {
		
		String url = mlApiUrl + "/gadgets";
		
		// create request object
		
		ObjectNode requestBody = objectMapper.createObjectNode();
		requestBody.put("productId", productId);
		
		
		// make HTTP request
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
		String responseAsStr = restTemplate.postForObject(url, request, String.class);
		JsonNode response = objectMapper.readTree(responseAsStr);
		
		
		// retrieve product IDs from response object
		
		ArrayNode productIdsNode = (ArrayNode) response.get("response").get("productIds");
		List<String> productIds = new ArrayList<>();
		
		if (null != productIdsNode) {
			for (JsonNode item : productIdsNode)
				productIds.add(item.asText());
		}

		return productIds;
	}

	public List<String> similarBoughtRecommendation(String productId) throws IOException {
		
		String url = mlApiUrl + "/similar-bought";
		
		// create request object
		
		ObjectNode requestBody = objectMapper.createObjectNode();
		requestBody.put("productId", productId);
		
		
		// make http request
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
		String responseAsStr = restTemplate.postForObject(url, request, String.class);
		JsonNode response = objectMapper.readTree(responseAsStr);
		
		System.out.println(response);
		
		
		// retrieve product IDs from response object
		
		ArrayNode productIdsNode = (ArrayNode) response.get("response").get("productIds");
		List<String> productIds = new ArrayList<>();
		
		if (null != productIdsNode) {
			for (JsonNode item : productIdsNode)
				productIds.add(item.asText());
		}

		return productIds;
	}
}

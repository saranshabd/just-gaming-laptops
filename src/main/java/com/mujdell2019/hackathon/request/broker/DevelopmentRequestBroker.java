package com.mujdell2019.hackathon.request.broker;

/**
 * NOTE
 * ----
 * 
 * THIS REQUEST BROKER IS ONLY USED FOR DEVELOPMENT PURPOSES AND IS NOT SUPPOSED TO BE INCLUDED
 * IN THE PRODUCTION API
 * 
 * COMMENT OUT ALL METHODS BEFORE PUSHING THE CODE
 * 
 * */

/*@RestController
@RequestMapping("/dev")
public class DevelopmentRequestBroker {
	
	@Autowired
	private DevelopmentRequestHandler requestHandler;
	
	@PostMapping("/dell/laptop/multiple")
	public ResponseEntity<APIResponse> addDellMultipleLaptops(@RequestBody JsonNode requestBody) {
		
		try {
			// Dell Products
			
			ArrayNode productNodes = (ArrayNode) requestBody.get("products");
			List<DellProductDBModel> dellProducts = new ArrayList<>();
			
			for (JsonNode product : productNodes) {
				DellProductDBModel dellProduct = new DellProductDBModel();
				
				// product type
				dellProduct.setProductType(ProductType.MOUSE.name());
				
				// basic attributes
				dellProduct.setName(product.get("name").asText());
				dellProduct.setImageUrl(product.get("image_url").asText());
				dellProduct.setPrice(product.get("price").asInt());
				dellProduct.setBudgetClass(product.get("budget_class").asText());
				
				// features
				HashMap<String, String> features = new HashMap<>();
				dellProduct.setFeatures(features);
				
				dellProducts.add(dellProduct);
			}
			
			
			APIResponse response = requestHandler.addDellProducts(dellProducts);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			APIResponse errorResponse = new APIResponse("internal server error", HttpStatus.INTERNAL_SERVER_ERROR, null);
			return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
		}
	}
}*/

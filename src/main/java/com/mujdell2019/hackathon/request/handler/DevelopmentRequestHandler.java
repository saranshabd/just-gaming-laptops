package com.mujdell2019.hackathon.request.handler;

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

/*@Component
@Scope("singleton")
public class DevelopmentRequestHandler {
	
	@Autowired
	private DellProductDAO dellProductDAO;
	@Autowired
	private SearchedProductDAO searchedProductDAO;
	
	
	public APIResponse addDellProducts(List<DellProductDBModel> products) {
		
		// save dell products in DB
		dellProductDAO.addProducts(products);
		
		return new APIResponse("products added", HttpStatus.OK, null);
	}
	

	public APIResponse addDellLaptopsAndSearchedProducts(List<DellProductDBModel> products, List<SearchedProductDBModel> searchedProducts) {
		
		int i = 0;
		for (DellProductDBModel product : products) {

			// save dell product in DB
			dellProductDAO.addProduct(product);
			
			// get current productId
			String productId = product.getProductId();
			
			// replace all the product IDs in searched products with productId that are same as 'i'
			for (SearchedProductDBModel searchedProduct : searchedProducts) {
				try {
					int currIndex = Integer.parseInt(searchedProduct.getDellProductId());
					if (currIndex == i)
						searchedProduct.setDellProductId(productId);
				} catch (NumberFormatException e) {}
			}
			
			++i;
		}
		
		// save all searched products in DB
		searchedProductDAO.addProducts(searchedProducts);


		return new APIResponse("products added", HttpStatus.OK, null);
	}
}*/

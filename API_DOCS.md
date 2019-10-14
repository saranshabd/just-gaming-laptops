# API Documentation

<br>

## Authentication Routes

- *POST* `~/api/user/auth/register`
	<br>
	
	Register New User in Database.
	
	**Parameters**
	
  	```json
	{
		"username" : "<String>",
		"name"     : "<String>",
		"password" : "<String>"
	}
	```

- *POST* `~/api/user/auth/login`
	<br>
	
	Login Existing User
	
	**Parameters**
	
  	```json
	{
		"username" : "<String>",
		"password" : "<String>"
	}
	```

- *POST* `~/api/user/auth/signout`
	<br>
	
	Signout Existing User
	
	**Parameters**
	
  	```json
	{
		"username" : "<String>"
	}
	```

- *GET* `~/api/user/auth/verify?username={<String>}`
	<br>
	
	Check if username already exists

<br>
	
## Cart Routes

- *GET* `~/api/user/cart?username={<String>}`
	<br>
	
	Get all products in user cart

- *PUT* `~/api/user/cart/item`
	<br>
	
	Add item to user cart
	
	**Parameters**
	
  	```json
	{
		"username"  : "<String>",
		"productId" : "<String>"
	}
	```

- *DELETE* `~/api/user/cart/item`
	<br>
	
	Delete item from user cart
	
	**Parameters**
	
 	 ```json
	{
		"username"  : "<String>",
		"productId" : "<String>"
	}
	```

<br>

## Backend Analytics Routes

- *PUT* `~/api/analytics/event/click`
	<br>
	
	Register an event when user clicks on a product
	
	**Parameters**
	
	```json
	{
		"productId": "<String>"
	}
	```

- *PUT* `~/api/analytics/event/cart/add`
	<br>
	
	Register an event when user adds an item to cart
	
	**Parameters**
	
	```json
	{
		"productId": "<String>"
	}
	```

- *PUT* `~/api/analytics/event/cart/delete`
	<br>
	
	Register an event when user deletes an item from cart
	
	**Parameters**
	
	```json
	{
		"productId": "<String>"
	}
	```

- *PUT* `~/api/analytics/event/buy`
	<br>
	
	Register an event when user buys a product
	
	**Parameters**
	
	```json
	{
		"productId": "<String>"
	}
	```

- *GET* `~/api/analytics/product/top/buy?count={<Integer>}`
	<br>
	
	Get top bought Dell products

- *GET* `~/api/analytics/product/worst/buy?count={<Integer>}`
	<br>
	
	Get least bought Dell products

- *GET* `~/api/analytics/product/top/view?count={<Integer>}`
	<br>
	
	Get top viewed Dell products

- *GET* `~/api/analytics/order-conversion-rate?productId={<String>}`
	<br>
	
	Get order conversion rate of a particular Dell product

<br>

## Dynamic Sale Routes

- *GET* `~/api/sale`
	<br>
	
	Get sale fields

- *PUT* `~/api/sale`
	<br>
	
	Set sale fields
	
	**Parameters**
	```json
	{
		"saleDays": "<Integer>",
		"saleDiscount": "<Double>"
	}
	```

- *PUT* `~/api/sale/status`

	Set sale status for current/upcoming month
	
	**Parameters**
	```json
	{
		"saleStatus": "<Boolean>"
	}
	```

- *PUT* `~/api/sale/product`
	
	Add Dell products to cart.
	
	**Parameters**
	```json
	{
		"productId": "<String>"
	}
	```

- *DELETE* `~/api/sale/product`

	Delete Dell products from sale.
	
	**Parameters**
	```json
	{
		"productId": "<String>"
	}
	```

<br>

## Recommendation/ML Routes

- *POST* `~/api/recommend/product/search`

	Search Dell products.
	
	**Parameters**
	```json
	{
		"username": "<String>",
		"query": "<String>",
		"filters": "<Map>"
	}
	```

- *POST* `~/api/recommend/product/trending`

	Recommend products based on user browser history.
	
	**Parameters**
	```json
	{
		"username": "<String>"
	}
	```

- *POST* `~/api/recommend/product/browsing-history`

	Recommend products based on user activity on dell.com, and browser history
	
	**Parameters**
	``json
	{
		"username": "<String>"
	}
	```

- *POST* `~/api/recommend/product/gadgets`

	Recommend related gadgets to given product.
	
	**Parameters**
	```json
	{
		"username": "<String>",
		"productId": "<String>"
	}
	```

- *POST* `~/api/recommend/product/similar-bought`

	Recommend related gadgets to given product.
	
	**Parameters**
	```json
	{
		"username": "<String>",
		"productId": "<String>"
	}
	```



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

- *GET* `~/api/user/auth/verify`
	<br>
	
	Check if username already exists
	
	**Parameters**
	
  ```json
	{
		"username" : "<String>"
	}
	```

<br>
	
## Cart Routes

- *GET* `~/api/user/cart`
	<br>
	
	Get all products in user cart
	
	**Parameters**
	
  ```json
	{
		"username" : "<String>"
	}
	```

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

- *GET* `~/api/analytics/product/top/buy`
	<br>
	
	Get top bought Dell products
	
	**Parameters**
	
	```json
	{
		"count": "<Integer>"
	}
	```

- *GET* `~/api/analytics/product/worst/buy`
	<br>
	
	Get least bought Dell products
	
	**Parameters**
	
	```json
	{
		"count": "<Integer>"
	}
	```

- *GET* `~/api/analytics/product/top/view`
	<br>
	
	Get top viewed Dell products
	
	**Parameters**
	
	```json
	{
		"count": "<Integer>"
	}
	```

- *GET* `~/api/analytics/order-conversion-rate`
	<br>
	
	Get order conversion rate of a particular Dell product
	
	**Parameters**
	
	```json
	{
		"productId": "<String>"
	}
	```

<br>

## Dynamic Sale Routes

- *GET* `~/api/sale`
	<br>
	
	Get sale fields
	
	**Parameters**
	`None`

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

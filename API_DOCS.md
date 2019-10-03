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

# API Documentation

<br>

## Authentication Routes

- `~/api/user/auth/register`
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
	
- `~/api/user/auth/login`
	<br>
	Login Existing User
	
	**Parameters**
	
  ```json
	{
		"username" : "<String>",
		"password" : "<String>"
	}
	```

- `~/api/user/auth/signout`
	<br>
	Signout Existing User
	
	**Parameters**
	
  ```json
	{
		"username" : "<String>"
	}
	```

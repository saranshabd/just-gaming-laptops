package com.mujdell2019.hackathon.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mujdell2019.hackathon.models.api.APIResponse;

@Component
@Scope("singleton")
public class ExceptionUtils {

	public ResponseEntity<APIResponse> reportException(Exception e) {
		// log error
		e.printStackTrace();
		
		// send internal server error response to the endpoint user
		APIResponse errorResponse = new APIResponse("internal server error", HttpStatus.INTERNAL_SERVER_ERROR, null);
		return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
	}
}

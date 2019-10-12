package com.mujdell2019.hackathon.request.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mujdell2019.hackathon.models.api.APIResponse;
import com.mujdell2019.hackathon.request.handler.HomeRequestHandler;
import com.mujdell2019.hackathon.utils.ExceptionUtils;

@RestController
@RequestMapping("/home/product")
public class HomeRequestBroker {

	@Autowired
	private HomeRequestHandler requestHandler;
	@Autowired
	private ExceptionUtils exceptionUtil;
	
	
	@CrossOrigin
	@GetMapping("/search")
	public ResponseEntity<APIResponse> searchProducts(@RequestParam String username, @RequestParam String query) {
		
		try {
			APIResponse response = requestHandler.searchProducts(username, query);
			return new ResponseEntity<>(response, response.getStatus());
			
		} catch (Exception e) { return exceptionUtil.reportException(e); }
	}
}

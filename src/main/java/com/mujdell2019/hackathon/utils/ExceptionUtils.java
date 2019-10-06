package com.mujdell2019.hackathon.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.mujdell2019.hackathon.models.api.APIResponse;

@Component
@Scope("singleton")
public class ExceptionUtils {
	
	@Value("${server.admin.email}")
	private String serverAdminEmail;
	
	@Autowired
	public JavaMailSender emailSender;

	public ResponseEntity<APIResponse> reportException(Exception e) {
		// log error
		e.printStackTrace();
		
		// send email to server administrator about the server crashing
		notifyServerAdmin();
		
		// send internal server error response to the endpoint user
		String errorMessage = "Something went wrong. Try again after sometime";
		APIResponse errorResponse = new APIResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, null);
		return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
	}
	
	private void notifyServerAdmin() {
		
		String subject = "[ALERT] Server Crashed";
		String message = "Server of just-gaming-laptop just crashed. Check the logs ASAP.";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailMessage.setTo(serverAdminEmail);
		
		emailSender.send(mailMessage);
	}
}

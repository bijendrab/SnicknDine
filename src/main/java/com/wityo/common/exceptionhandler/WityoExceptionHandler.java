package com.wityo.common.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@ControllerAdvice
@RestController
public class WityoExceptionHandler {
	
	@ExceptionHandler
	public String handleAllControllerExceptions(Exception ex){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("errorMessage", ex.getMessage());
		String responsee = new Gson().toJson(response);
		return responsee;
	}

}

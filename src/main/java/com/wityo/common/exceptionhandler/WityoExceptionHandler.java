package com.wityo.common.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.wityo.common.exception.WityoGenericException;

	@RestControllerAdvice
	public class WityoExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<?> handleAllControllerExceptions(WityoGenericException ex){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("errorMessage", ex.getMessage());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleAllControllerExceptions(Exception ex, WebRequest request){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("errorMessage", ex.getMessage());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

}

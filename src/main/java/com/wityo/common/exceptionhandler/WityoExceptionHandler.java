package com.wityo.common.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wityo.common.exception.WityoGenericException;

@ControllerAdvice
public class WityoExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({WityoGenericException.class})
	public ResponseEntity<?> handleAllControllerExceptions(WityoGenericException ex){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("errorMessage", ex.getMessage());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<?> handleAllControllerExceptions(Exception ex, WebRequest request){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("errorMessage", ex.getMessage());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}

}

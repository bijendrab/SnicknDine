package com.wityo.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ResponseCreator {
	
	public static Map<String, Object> successResponseCreator(String msg, Object body, boolean error, HttpStatus status){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", msg);
		response.put("body", body);
		response.put("error", error);
		response.put("status", status);
		return response;
	}
	
	public static Map<String, Object> errorResponseCreator(String msg, Object body, boolean error, HttpStatus status){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", msg);
		response.put("body", body);
		response.put("error", error);
		response.put("status", status);
		return response;
	}

}

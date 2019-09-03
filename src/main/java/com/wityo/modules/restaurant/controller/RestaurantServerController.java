package com.wityo.modules.restaurant.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.common.Constant;
import com.wityo.modules.restaurant.service.RestaurantServerService;

@RestController
@RequestMapping(Constant.RESTAURANT_SERVER_API)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestaurantServerController {
	
	@Autowired
	RestaurantServerService restaurantService;
	
	@GetMapping(value = "/get-restaurants")
	public ResponseEntity<?> fetchRestaurants(){
		Map<String, Object> response = new HashMap<String, Object>();
		response.putIfAbsent("message", "Restaurant list by Id and Name");
		response.put("body", restaurantService.fetchRestaurantListByIdAndName());
		response.put("error", Boolean.FALSE);
		response.put("status", HttpStatus.FOUND);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/menu/{restaurantId}")
	public ResponseEntity<?> fetchRestaurantMenu(@PathVariable Long restaurantId){
		Map<String, Object> response = new HashMap<String, Object>();
		response.putIfAbsent("message", "Restaurant menu list");
		response.put("body", restaurantService.getRestaurantMenuByRestId(restaurantId));
		response.put("error", Boolean.FALSE);
		response.put("status", HttpStatus.FOUND);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}

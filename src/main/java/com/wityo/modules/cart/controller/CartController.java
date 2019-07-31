
package com.wityo.modules.cart.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.modules.cart.service.CartService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cart")
public class CartController {

	Logger logger = LoggerFactory.getLogger(CartController.class);
	@Autowired
	private CartService cartService;
	
	@GetMapping("/getcart")
	public ResponseEntity<?> fetchCartById(){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "user cart items");
		response.put("body", cartService.getCart());
		response.put("status", HttpStatus.ACCEPTED);
		response.put("error", false);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}
	
}
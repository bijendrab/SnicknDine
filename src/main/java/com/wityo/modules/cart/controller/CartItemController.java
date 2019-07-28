package com.wityo.modules.cart.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.user.model.User;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cartitem")
public class CartItemController {
	
	@Autowired
	private CartItemService cartItemService;		

	
	/*
	 * @Description: Function to add/update CartItem in user's cart.
	 * 
	 * */
	@PostMapping("/addupdate/{productId}/{quantityOption}")
	public ResponseEntity<?> addOrUpdateCartItem(@PathVariable String productId, @PathVariable String quantityOption){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", cartItemService.addOrUpdateCart(productId,quantityOption));
		response.put("body", "");
		response.put("status", HttpStatus.ACCEPTED);
		response.put("error", false);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}

	/*
	 * @Description: Function to remove CartItem completely from user's cart
	 * 
	 * */
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<?> removeCartItem(@PathVariable String cartItemId){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", cartItemService.deleteCartItemById(Long.parseLong(cartItemId)));
		response.put("body", "");
		response.put("status", HttpStatus.ACCEPTED);
		response.put("error", false);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}
	
	
	/*
	 * @Description: Function to remove all CartItems from user's cart
	 * 
	 * */
	@DeleteMapping("/delete/cartitems")
	public ResponseEntity<?> removeAllCartItems(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", cartItemService.removeAllCartItems(user.getCustomer().getCart()));
		response.put("body", "");
		response.put("status", HttpStatus.ACCEPTED);
		response.put("error", false);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}
	
	
	/*
	 * @Description: Function to Decrement or Remove cartItem from cart
	 * 
	 * */
	@DeleteMapping("/decrement/{productId}/{quantityOption}")
	public ResponseEntity<?> decrementCartItemQuantity(@PathVariable Long productId, @PathVariable String quantityOption){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", cartItemService.reduceCartItem(productId, quantityOption));
		response.put("body", "");
		response.put("status", HttpStatus.ACCEPTED);
		response.put("error", false);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}
}

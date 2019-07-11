package com.wityo.modules.cart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.cart.service.CartService;
import com.wityo.modules.user.model.Customer;
import com.wityo.modules.user.model.User;
import com.wityo.modules.user.service.CustomerService;
import com.wityo.modules.user.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/getcart")
	public ResponseEntity<?> fetchCartById(){
		try {
			User userDetail = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Customer customer = customerService.getCustomerByPhoneNumber(userDetail.getPhoneNumber());
			List<CartItem> cartItems = customer.getCart().getCartItems();
			return new ResponseEntity<Object>(cartItems, HttpStatus.FOUND);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
}
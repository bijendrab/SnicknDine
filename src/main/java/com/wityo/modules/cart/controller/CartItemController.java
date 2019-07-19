package com.wityo.modules.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.modules.cart.service.CartItemService;
import com.wityo.modules.cart.service.CartService;
import com.wityo.modules.user.service.CustomerService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cart")
public class CartItemController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartItemService cartItemService;		

	@Autowired
	private CustomerService customerService;

	
	
}

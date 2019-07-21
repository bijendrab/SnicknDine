package com.wityo.modules.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.common.Constant;
import com.wityo.modules.order.dto.ImmediateRequestDto;
import com.wityo.modules.order.service.OrderService;
import com.wityo.modules.user.model.User;

@CrossOrigin("*")
@RestController
@RequestMapping(Constant.ORDER_API)
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	ResponseEntity<?> orderResponseBuilder(String msg, Object body, boolean error, HttpStatus status){
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", msg);
		response.put("body", body);
		response.put("error", error);
		response.put("status", status);
		if(error)
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/placeorder")
	public ResponseEntity<?> placeOrder(@RequestBody ImmediateRequestDto order){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return orderResponseBuilder("Order placed successfully",
				orderService.placeOrder(user.getCustomer(),order),
				false,
				HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getorder")
	public ResponseEntity<?> getCustomerOrder(){
		return orderResponseBuilder("Order placed successfully",
				orderService.getCustomersOrder(),
				false,
				HttpStatus.CREATED);
	}
}

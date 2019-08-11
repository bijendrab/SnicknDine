package com.wityo.modules.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wityo.common.Constant;
import com.wityo.modules.order.dto.PlaceOrderDTO;
import com.wityo.modules.order.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping(Constant.ORDER_API)
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/checkout/{restaurantId}")
	public ResponseEntity<?> placeOrder(@PathVariable Long restaurantId, @RequestBody PlaceOrderDTO order){
		Map<String, Object> response = new HashMap<String, Object>();
		response.putIfAbsent("message", "Reservation status");
		response.put("body", orderService.placeCustomerOrder(order, restaurantId));
		response.put("error", Boolean.FALSE);
		response.put("status", HttpStatus.OK);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}

package com.wityo.modules.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wityo.common.Constant;
import com.wityo.modules.order.model.OrderItem;
import com.wityo.modules.order.service.OrderItemService;

@CrossOrigin("*")
@RestController
@RequestMapping(Constant.ORDER_ITEM_API)
public class OrderItemController {
	
	@Autowired
	OrderItemService orderItemService;
	
	ResponseEntity<?> orderItemResponseBuilder(String msg, Object body, boolean error, HttpStatus status){
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
	
	@PutMapping(value = "/updateorderitem")
	public ResponseEntity<?> updateOrderItem(@RequestBody OrderItem orderItem){
		return orderItemResponseBuilder("Order Item updated",
				orderItemService.updateOrder_orderItem(orderItem),
				false,
				HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteorderitem")
	public ResponseEntity<?> deleteOrderItem(@RequestBody OrderItem orderItem){
		return orderItemResponseBuilder("Order Item updated",
				orderItemService.deleteCustomerOrderItem(orderItem.getOrderItemId()),
				false,
				HttpStatus.OK);
	}
}

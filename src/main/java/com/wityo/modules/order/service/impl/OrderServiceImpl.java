package com.wityo.modules.order.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wityo.modules.order.model.OrderItem;
import com.wityo.modules.order.repository.OrderItemRepository;
import com.wityo.modules.order.repository.OrderRepository;
import com.wityo.modules.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public String updateOrderItem(OrderItem orderItem) {
		Optional<OrderItem> optionalItem = orderItemRepository.findById(orderItem.getOrderItemId());
		OrderItem existingOrderItem = null;
		if(optionalItem.isPresent()) {
			existingOrderItem = optionalItem.get();
			return "updated";
		}
		return "not updated";
		
	}
	
}

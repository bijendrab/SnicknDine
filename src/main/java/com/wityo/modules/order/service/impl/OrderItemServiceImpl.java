package com.wityo.modules.order.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wityo.modules.order.model.Order;
import com.wityo.modules.order.model.OrderItem;
import com.wityo.modules.order.repository.OrderItemRepository;
import com.wityo.modules.order.repository.OrderRepository;
import com.wityo.modules.product.model.ProductQuantityOption;

//CustomerOrderService old = OrderItemService new

@Service
public class OrderItemServiceImpl {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	
	/*
	 * 
	 * @Description:- This method is used to fetch all the orders that the user has made,
	 * including the current order 
	 * 
	 * */
	List<Map<Long, List<OrderItem>>> getCustomerOrderList(){
		return null;
	}

	
	/*
	 * @Description: Call this method when you want to update an Order Item after the order is placed,
	 * provided it is eligible for getting updated.
	 * 
	 * @Note: Only productQuantityOption and item quantity can be updated from the front end.
	 * */
	public Order updateOrder_orderItem(OrderItem orderItem) {
		Optional<OrderItem> optionalOI = orderItemRepository.findById(orderItem.getOrderItemId());
		if(optionalOI.isPresent()) {
			OrderItem orderItemTbu = optionalOI.get();
			Long orderItemOrderId = orderItemTbu.getOrder().getOrderId();
			Order orderTbu = orderRepository.findByOrderId(orderItemOrderId);
			double oldOrderItemCost = orderItemTbu.getPrice();
			double updatedItemCost = 0.0;
			/*
			 * Loop through the product quantity option ofr the product hold by OrderItem and then when match
			 * is found multiply the quantity with the product quantity option price
			 * 
			 * */
			for(ProductQuantityOption pqo : orderItemTbu.getProduct().getProductQuantityOptions()) {
				if(orderItem.getQuantityOption().equals(pqo.getQuantityOption())) {
					updatedItemCost = orderItem.getQuantity() * pqo.getPrice(); 
					break;
				}
			}
			orderItemTbu.setProduct(orderItem.getProduct());
			orderItemTbu.setPrice(updatedItemCost);
			double updatedOrderCost = orderTbu.getTotalCost() - oldOrderItemCost + updatedItemCost;
			orderTbu.setTotalCost(updatedOrderCost);
			orderItemRepository.save(orderItemTbu);
			return orderRepository.saveAndFlush(orderTbu);
		}
		return null;
	}
	
	/*
	 * @Description: Call this method when you want to delete an Order Item after the order is placed,
	 * provided it is eligible for getting updated.
	 * 
	 * */
	public Order deleteCustomerOrderItem(Long orderItemId) {
		Optional<OrderItem> optionalOI = orderItemRepository.findById(orderItemId);
		if(optionalOI.isPresent()) {
			OrderItem tbdOrderItem = optionalOI.get();
			Order orderOfOrderItem = orderRepository.findByOrderId(tbdOrderItem.getOrder().getOrderId());
			double priceOfTbItem = tbdOrderItem.getPrice();
			double updatedTotalCost = orderOfOrderItem.getTotalCost() - priceOfTbItem;
			orderOfOrderItem.setTotalCost(updatedTotalCost);
			orderItemRepository.deleteById(orderItemId);
			orderRepository.saveAndFlush(orderOfOrderItem);
			return orderOfOrderItem; 
		}
		return null;
	}
	
}

package com.wityo.modules.order.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.model.OrderItem;
import com.wityo.modules.order.repository.CustomerOrderRepository;
import com.wityo.modules.order.repository.OrderItemRepository;
import com.wityo.modules.order.service.OrderItemService;
import com.wityo.modules.product.model.Product;
import com.wityo.modules.product.model.ProductQuantityOption;

//CustomerOrderService old = OrderItemService new

@Service
public class OrderItemServiceImpl implements OrderItemService{
	
	@Autowired
	CustomerOrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	
	/*
	 * 
	 * @Description:- This method is used to fetch all the orders that the user has made,
	 * including the current order 
	 * 
	 * */
	public List<CustomerOrder> getCustomerOrderList(){
		return null;
	}

	
	/*
	 * @Description: Call this method when you want to update an CustomerOrder Item after the order is placed,
	 * provided it is eligible for getting updated.
	 * 
	 * @Note: Only productQuantityOption and item quantity can be updated from the front end.
	 * */
	public CustomerOrder updateOrder_orderItem(OrderItem orderItem) {
		Optional<OrderItem> optionalOI = orderItemRepository.findById(orderItem.getOrderItemId());
		if(optionalOI.isPresent()) {
			OrderItem orderItemTbu = optionalOI.get();
			Long orderItemOrderId = orderItemTbu.getCustomerOrder().getCustomerOrderId();
			CustomerOrder orderTbu = orderRepository.findByCustomerOrderId(orderItemOrderId);
			double oldOrderItemCost = orderItemTbu.getPrice();
			double updatedItemCost = 0.0;
			/*
			 * Loop through the product quantity option of the product hold by OrderItem and then when match
			 * is found multiply the quantity with the product quantity option price
			 * 
			 * */
			Set<ProductQuantityOption> productQuantityOptions = new Gson().fromJson(orderItemTbu.getProductJson(), Product.class).getProductQuantityOptions();
			for(ProductQuantityOption pqo : productQuantityOptions) {
				if(orderItem.getQuantityOption().equals(pqo.getQuantityOption())) {
					updatedItemCost = orderItem.getQuantity() * pqo.getPrice(); 
					break;
				}
			}
			orderItemTbu.setProductJson(orderItem.getProductJson());
			orderItemTbu.setPrice(updatedItemCost);
			double updatedOrderCost = orderTbu.getTotalCost() - oldOrderItemCost + updatedItemCost;
			orderTbu.setTotalCost(updatedOrderCost);
			orderItemRepository.save(orderItemTbu);
			return orderRepository.saveAndFlush(orderTbu);
		}
		return null;
	}
	
	/*
	 * @Description: Call this method when you want to delete an CustomerOrder Item after the order is placed,
	 * provided it is eligible for getting updated.
	 * 
	 * */
	public CustomerOrder deleteCustomerOrderItem(Long orderItemId) {
		Optional<OrderItem> optionalOI = orderItemRepository.findById(orderItemId);
		if(optionalOI.isPresent()) {
			OrderItem tbdOrderItem = optionalOI.get();
			CustomerOrder orderOfOrderItem = orderRepository.findByCustomerOrderId(tbdOrderItem.getCustomerOrder().getCustomerOrderId());
			double priceOfTbItem = tbdOrderItem.getPrice();
			double updatedTotalCost = orderOfOrderItem.getTotalCost() - priceOfTbItem;
			orderOfOrderItem.setTotalCost(updatedTotalCost);
			orderItemRepository.deleteById(orderItemId);
			orderRepository.saveAndFlush(orderOfOrderItem);
			return orderOfOrderItem; 
		}
		return null;
	}
	
	public CustomerOrder addItemToOrder(CustomerOrder order, Product newItem, Long itemOrderedNumber) {
		return null;
	}
}

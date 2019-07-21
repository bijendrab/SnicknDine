package com.wityo.modules.order.service;

import java.util.List;

import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.model.OrderItem;
import com.wityo.modules.product.model.Product;

public interface OrderItemService {
	
	List<CustomerOrder> getCustomerOrderList();
	public CustomerOrder updateOrder_orderItem(OrderItem orderItem);
	public CustomerOrder deleteCustomerOrderItem(Long orderItemId);
	public CustomerOrder addItemToOrder(CustomerOrder order, Product newItem, Long itemOrderedNumber);

}

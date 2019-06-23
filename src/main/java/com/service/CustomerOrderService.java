package com.service;

import com.model.Cart;
import com.model.CustomerOrder;
import com.model.OrderItem;
import com.model.Product;

import java.util.List;
import java.util.Map;

public interface CustomerOrderService {

	void addCustomerOrder(CustomerOrder customerOrder);
	double getCustomerOrderGrandTotal(int cartId);
	List<Map<Integer,List<OrderItem>>> getCustomerOrder();
	void updateCustomerOrder(OrderItem orderitem);
}

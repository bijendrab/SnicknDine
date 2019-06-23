package com.dao;

import com.model.Cart;
import com.model.CustomerOrder;
import com.model.OrderItem;
import com.model.Product;

import java.util.List;
import java.util.Map;

public interface CustomerOrderDao {

	void addCustomerOrder(CustomerOrder customerOrder);
	List<Map<Integer,List<OrderItem>>> getCustomerOrderByCustomerId();
	void updateCustomerOrderItem(OrderItem orderitem);
}

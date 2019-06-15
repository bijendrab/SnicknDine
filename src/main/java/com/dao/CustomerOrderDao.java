package com.dao;

import com.model.Cart;
import com.model.CustomerOrder;
import com.model.OrderItem;
import com.model.Product;

import java.util.List;

public interface CustomerOrderDao {

	void addCustomerOrder(CustomerOrder customerOrder);
	List<OrderItem> getCustomerOrderByCustomerId();
	void updateCustomerOrderItem(OrderItem orderitem);
}

package com.dao;

import com.model.Cart;
import com.model.CustomerOrder;
import com.model.Product;

import java.util.List;

public interface CustomerOrderDao {

	void addCustomerOrder(CustomerOrder customerOrder);
	public List<Cart> getCustomerOrderByCustomerId();
}

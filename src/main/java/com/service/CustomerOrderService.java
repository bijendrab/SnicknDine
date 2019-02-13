package com.service;

import com.model.Cart;
import com.model.CustomerOrder;
import com.model.Product;

import java.util.List;

public interface CustomerOrderService {

	void addCustomerOrder(CustomerOrder customerOrder);
	double getCustomerOrderGrandTotal(int cartId);
	public List<Cart> getCustomerOrder();
}

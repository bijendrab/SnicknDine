package com.wityo.modules.order.dto;

import java.util.List;

import com.wityo.modules.cart.model.CartItem;
import com.wityo.modules.user.model.Customer;

public class PlaceOrderDTO {
	private List<CartItem> cartItems;
	private Customer customer;

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}

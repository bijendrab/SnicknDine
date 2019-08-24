package com.wityo.modules.order.dto;

import com.wityo.modules.user.model.Customer;

public class UpdateOrderItemDTO {
	
	private int orderItemId;
	private String quantity;
	private String quantityOption;
	Customer customer;
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getQuantityOption() {
		return quantityOption;
	}
	public void setQuantityOption(String quantityOption) {
		this.quantityOption = quantityOption;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

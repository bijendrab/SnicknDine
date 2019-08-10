package com.wityo.modules.order.model;

import java.time.LocalDateTime;

import com.wityo.modules.cart.model.Cart;

public class OrderItem {
	
	private Long orderItemId;
	private LocalDateTime orderCreationTime;
	private int quantity;
	private double price;
	private String status;

	private String itemName;
	private double waitTime;
	private String quantityOption;
	private Boolean immediateStatus;
	private String productJson;
	private Cart cart;
	private CustomerOrder customerOrder;
	
	public OrderItem() {}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public LocalDateTime getOrderCreationTime() {
		return orderCreationTime;
	}

	public void setOrderCreationTime(LocalDateTime orderCreationTime) {
		this.orderCreationTime = orderCreationTime;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(double waitTime) {
		this.waitTime = waitTime;
	}

	public String getQuantityOption() {
		return quantityOption;
	}

	public void setQuantityOption(String quantityOptions) {
		this.quantityOption = quantityOptions;
	}

	public Boolean getImmediateStatus() {
		return immediateStatus;
	}

	public void setImmediateStatus(Boolean immediateStatus) {
		this.immediateStatus = immediateStatus;
	}


	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getProductJson() {
		return productJson;
	}

	public void setProductJson(String productJson) {
		this.productJson = productJson;
	}
}

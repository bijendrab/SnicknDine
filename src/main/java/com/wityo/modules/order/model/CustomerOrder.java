package com.wityo.modules.order.model;

import java.util.HashSet;
import java.util.Set;

import com.wityo.modules.reservation.model.Reservation;

public class CustomerOrder {

	private Long orderId;
	private Float totalCost;
	private OrderStatus status;
	private Reservation accordingReservation;
	private Set<OrderItem> menuItemOrders = new HashSet<OrderItem>();
	private String orderedBy;

	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Reservation getAccordingReservation() {
		return accordingReservation;
	}
	public void setAccordingReservation(Reservation accordingReservation) {
		this.accordingReservation = accordingReservation;
	}
	public Set<OrderItem> getMenuItemOrders() {
		return menuItemOrders;
	}
	public void setMenuItemOrders(Set<OrderItem> menuItemOrders) {
		this.menuItemOrders = menuItemOrders;
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}
}

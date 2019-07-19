package com.wityo.modules.order.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	private double totalCost;
	private OrderStatus status;
	@OneToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	private Set<OrderItem> menuItemOrders;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	public Set<OrderItem> getMenuItemOrders() {
		return menuItemOrders;
	}
	public void setMenuItemOrders(Set<OrderItem> menuItemOrders) {
		this.menuItemOrders = menuItemOrders;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

}

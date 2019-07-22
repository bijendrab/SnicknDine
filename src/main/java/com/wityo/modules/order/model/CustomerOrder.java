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
import javax.persistence.Table;

import com.wityo.modules.reservation.model.Reservation;

@Entity
@Table(name = "customer_order")
public class CustomerOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerOrderId;
	private double totalCost;
	private OrderStatus status;
	@OneToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;
	@OneToMany(mappedBy = "customerOrder",cascade = CascadeType.ALL)
	private Set<OrderItem> menuItemOrders;
	public Long getCustomerOrderId() {
		return customerOrderId;
	}
	public void setCustomerOrderId(Long orderId) {
		this.customerOrderId = orderId;
	}
	public double getTotalCost() {
		return totalCost;
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

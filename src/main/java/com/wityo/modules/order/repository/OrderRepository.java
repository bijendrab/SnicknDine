package com.wityo.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityo.modules.order.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public Order findByOrderId(Long orderId);
	public Order findByReservationId(Long reservationId);

}

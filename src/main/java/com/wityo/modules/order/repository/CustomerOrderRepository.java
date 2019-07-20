package com.wityo.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wityo.modules.order.model.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	
	public CustomerOrder findByCustomerOrderId(Long orderId);
//	public CustomerOrder findByReservationId(Long reservationId);

}

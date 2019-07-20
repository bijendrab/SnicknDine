package com.wityo.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wityo.modules.order.model.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	
	public CustomerOrder findByCustomerOrderId(Long orderId);
	@Query(value = "select * from customer_order where reservation_id = ?1",nativeQuery = true)
	public CustomerOrder findByReservation(Long reservationId);

}

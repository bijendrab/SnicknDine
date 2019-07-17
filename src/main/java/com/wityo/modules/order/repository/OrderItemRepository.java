package com.wityo.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wityo.modules.order.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}

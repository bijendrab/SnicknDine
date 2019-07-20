package com.wityo.modules.order.service;

import com.wityo.modules.order.dto.ImmediateRequestDto;
import com.wityo.modules.order.model.CustomerOrder;
import com.wityo.modules.order.model.OrderStatus;
import com.wityo.modules.order.model.Reservation;
import com.wityo.modules.product.model.Product;

public interface OrderService {
	
	Reservation findReservationByOrder(CustomerOrder order);
	CustomerOrder saveOrderForReservation(Reservation reservation, CustomerOrder newOrder);
	CustomerOrder getOrderById(Long id);
	CustomerOrder changeOrderStatus(CustomerOrder order, OrderStatus orderStatus);
	CustomerOrder confirmOrder(Long orderId);
	CustomerOrder addItemToOrder(CustomerOrder order, Product newItem, Long itemOrderedNumber);
	CustomerOrder findOrderByReservation(Reservation resevation);
	CustomerOrder processOrderRequest(Long cartId, Long customerId, ImmediateRequestDto requestDto);
}
